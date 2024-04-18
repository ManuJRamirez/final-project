package com.wallaclone.finalproject.service.impl;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wallaclone.finalproject.dto.ImagenDto;
import com.wallaclone.finalproject.dto.RequestAnunciosFiltradosDto;
import com.wallaclone.finalproject.dto.ResponseAnuncioDto;
import com.wallaclone.finalproject.dto.ResponseCategoriaDto;
import com.wallaclone.finalproject.entity.Anuncios;
import com.wallaclone.finalproject.entity.Categoria;
import com.wallaclone.finalproject.entity.Usuario;
import com.wallaclone.finalproject.repository.AnunciosRepository;
import com.wallaclone.finalproject.repository.AnunciosTagsRepository;
import com.wallaclone.finalproject.repository.CategoriasRepository;
import com.wallaclone.finalproject.repository.UsuariosRepository;
import com.wallaclone.finalproject.service.AnunciosService;
import com.wallaclone.finalproject.utils.ApplicationConstants;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

@Service
public class AnunciosServiceImpl implements AnunciosService {

	@Autowired
	AnunciosRepository anunciosRepository;

	@Autowired
	UsuariosRepository usuarioRepository;

	@Autowired
	AnunciosTagsRepository anunciosTagsRepository;

	@Autowired
	CategoriasRepository categoriasRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	@Transactional
	public ResponseAnuncioDto getAnuncio(String id) {

		Anuncios anuncio = anunciosRepository.findById(Long.valueOf(id)).get();
		ResponseAnuncioDto responseAnuncioDto = modelMapper.map(anuncio, ResponseAnuncioDto.class);

		responseAnuncioDto.setApodoCreador(anuncio.getUsuario().getApodo());

		List<String> listCategorias = new ArrayList<>();
		anuncio.getCategorias().forEach(categoria -> {
			listCategorias.add(categoria.getNombre());
		});
		responseAnuncioDto.setListCategoria(listCategorias);

		List<ImagenDto> listImagenDto = new ArrayList<>();
		anuncio.getImagenes().forEach(imagen -> {
			ImagenDto imagenDto = modelMapper.map(imagen, ImagenDto.class);
			imagenDto.setImagenResize(transformarTamImg(imagen.getImagen(), ApplicationConstants.TAMANO_ANCHO_DETALLE_ANUNCIO, ApplicationConstants.TAMANO_ALTO_DETALLE_ANUNCIO));
			listImagenDto.add(imagenDto);
		});
		responseAnuncioDto.setListImagenes(listImagenDto);
		return responseAnuncioDto;
	}

	@Override
	@Transactional
	public List<ResponseCategoriaDto> getCategorias() {
		return categoriasRepository.findAll().stream()
				.map(categoria -> modelMapper.map(categoria, ResponseCategoriaDto.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Page<ResponseAnuncioDto> getAnunciosFiltrados(RequestAnunciosFiltradosDto request) {
		PageRequest paginaRequest = PageRequest.of(request.getPagina(), ApplicationConstants.TAMANO_PAGINA);

		Specification<Anuncios> spec = (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (request.getCategorias() != null && !request.getCategorias().isEmpty()) {// in categorias
				Join<Anuncios, Categoria> categoriaJoin = root.join("categorias", JoinType.INNER);
				In<String> inClause = criteriaBuilder.in(categoriaJoin.get("nombre"));
				for (String categoria : request.getCategorias()) {
					inClause.value(categoria);
				}
				predicates.add(inClause);
			}

			if (request.getTitulo() != null && !request.getTitulo().isEmpty()) {
				predicates.add(criteriaBuilder.like(root.get("titulo"), "%" + request.getTitulo() + "%"));
			}

			if (request.getPrecioMin() > 0) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("precio"), request.getPrecioMin()));
			}

			if (request.getPrecioMax() > 0) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("precio"), request.getPrecioMax()));
			}

			if (request.isTransaccion() != null) {
				predicates.add(criteriaBuilder.equal(root.get("transacion"), request.isTransaccion()));
			}
			
			if (request.getUsuario() != null && !request.getUsuario().isEmpty()) {
				Join<Anuncios, Usuario> usuarioJoin = root.join("usuario", JoinType.INNER);
				predicates.add(criteriaBuilder.equal(usuarioJoin.get("apodo"), request.getUsuario()));
			}
			
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			
		};

		Page<Anuncios> paginacionAnuncios;

		switch (request.getOrden()) {
		case ApplicationConstants.ORDEN_RECIENTE:
			paginaRequest = PageRequest.of(request.getPagina(), ApplicationConstants.TAMANO_PAGINA,
					Sort.by("fechaCreacion").and(Sort.by("id")).descending());
			break;
		case ApplicationConstants.ORDEN_ANTIGUOS:
			paginaRequest = PageRequest.of(request.getPagina(), ApplicationConstants.TAMANO_PAGINA,
					Sort.by("fechaCreacion").and(Sort.by("id")).ascending());
			break;
		case ApplicationConstants.ORDEN_PRECIO_MIN:
			paginaRequest = PageRequest.of(request.getPagina(), ApplicationConstants.TAMANO_PAGINA,
					Sort.by("precio").and(Sort.by("id")).ascending());
			break;
		case ApplicationConstants.ORDEN_PRECIO_MAX:
			paginaRequest = PageRequest.of(request.getPagina(), ApplicationConstants.TAMANO_PAGINA,
					Sort.by("precio").and(Sort.by("id")).descending());
			break;
		default:
			break;
		}
		paginacionAnuncios = anunciosRepository.findAll(spec, paginaRequest);

		return paginacionAnuncios.map(anuncio -> {
			ResponseAnuncioDto res = modelMapper.map(anuncio, ResponseAnuncioDto.class);

			res.setApodoCreador(anuncio.getUsuario().getApodo());

			List<String> listCategorias = new ArrayList<>();
			anuncio.getCategorias().forEach(categoria -> {
				listCategorias.add(categoria.getNombre());
			});
			res.setListCategoria(listCategorias);

			List<ImagenDto> listImagenes = new ArrayList<ImagenDto>();
			anuncio.getImagenes().forEach(imagen -> {
				ImagenDto imagenDto = modelMapper.map(imagen, ImagenDto.class);
				imagenDto.setImagen(transformarTamImg(imagen.getImagen(), ApplicationConstants.TAMANO_ANCHO_LIST_ANUNCIO, ApplicationConstants.TAMANO_ALTO_LIST_ANUNCIO));
				listImagenes.add(imagenDto);
			});
			res.setListImagenes(listImagenes);
			return res;
		});
	}
	
	private byte[] transformarTamImg(byte[] imgOriginal, int targetWidth, int targetHeight) {
		// Convertir los bytes en BufferedImage
		ByteArrayInputStream bis = new ByteArrayInputStream(imgOriginal);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			BufferedImage originalImage = ImageIO.read(bis);

			int originalWidth = originalImage.getWidth();
			int originalHeight = originalImage.getHeight();

			// Calcular el aspect ratio deseado
			double aspectRatio = (double) targetWidth / targetHeight;

			// Calcular el aspect ratio de la imagen original
			double imageAspectRatio = (double) originalWidth / originalHeight;

			// Inicializar las dimensiones para el área de recorte
			int newWidth = originalWidth;
			int newHeight = originalHeight;

			// Ajustar las dimensiones para mantener el aspect ratio deseado
			if (imageAspectRatio > aspectRatio) {
				// La imagen original es más ancha, recortamos los lados
				newWidth = (int) (originalHeight * aspectRatio);
			} else {
				// La imagen original es más alta, recortamos la parte superior e inferior
				newHeight = (int) (originalWidth / aspectRatio);
			}

			// Calcular las coordenadas para el recorte centrado
			int x = (originalWidth - newWidth) / 2;
			int y = (originalHeight - newHeight) / 2;

			// Recortar la imagen original según las dimensiones calculadas
			BufferedImage croppedImage = originalImage.getSubimage(x, y, newWidth, newHeight);

			// Escalar la imagen recortada al tamaño deseado
			Image scaledImage = croppedImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

			// Crear una nueva imagen vacía con las dimensiones deseadas
			BufferedImage finalImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

			// Dibujar la imagen escalada en la nueva imagen
			Graphics2D g2d = finalImage.createGraphics();
			g2d.drawImage(scaledImage, 0, 0, null);
			g2d.dispose();

			// Escribir la imagen final en el flujo de salida
			ImageIO.write(finalImage, "png", bos);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}


}
