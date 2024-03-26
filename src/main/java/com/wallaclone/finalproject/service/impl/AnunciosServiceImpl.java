package com.wallaclone.finalproject.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wallaclone.finalproject.dto.RequestAnunciosFiltradosDto;
import com.wallaclone.finalproject.dto.ResponseAnuncioDto;
import com.wallaclone.finalproject.dto.ResponseCategoriaDto;
import com.wallaclone.finalproject.entity.Anuncio;
import com.wallaclone.finalproject.entity.AnuncioTags;
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
	public List<ResponseAnuncioDto> getAnuncios() {
		return anunciosRepository.findAll().stream().map(anuncio -> {
			ResponseAnuncioDto res = modelMapper.map(anuncio, ResponseAnuncioDto.class);
			Optional<Usuario> usuario = usuarioRepository.findById((long) anuncio.getIdUsuario());
			res.setApodoCreador(usuario.get().getApodo());
			return res;
		}).collect(Collectors.toList());
	}

	@Override
	public ResponseAnuncioDto getAnuncio(String id) {
		
		Anuncio anuncio = anunciosRepository.findById(Long.valueOf(id)).get();
		ResponseAnuncioDto responseAnuncioDto = modelMapper.map(anuncio, ResponseAnuncioDto.class);
		
		Optional<Usuario> usuario = usuarioRepository.findById((long) anuncio.getIdUsuario()); 		
		responseAnuncioDto.setApodoCreador(usuario.get().getApodo());
		
		List<String> listCategorias = new ArrayList<>();
		List<Optional<AnuncioTags>> anunciosTags = anunciosTagsRepository.findByIdAnuncio((long) anuncio.getId()); 
		anunciosTags.forEach(optionalAnuncioTag -> {
			Optional<Categoria> categoria = categoriasRepository.findById((long) optionalAnuncioTag.get().getIdCategoria());
			listCategorias.add(categoria.get().getNombre());
		});
		responseAnuncioDto.setListCategoria(listCategorias);
		
		return responseAnuncioDto;
	}

	@Override
	public List<ResponseCategoriaDto> getCategorias() {		
		return categoriasRepository.findAll().stream()
				.map(categoria -> modelMapper.map(categoria, ResponseCategoriaDto.class)).collect(Collectors.toList());
	}
	
	@Override
	public Page<ResponseAnuncioDto> getAnunciosFiltrados(RequestAnunciosFiltradosDto request) {
        PageRequest paginaRequest = PageRequest.of(request.getPagina(), ApplicationConstants.TAMANO_PAGINA);
        
        Specification<Anuncio> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getCategorias() != null && !request.getCategorias().isEmpty()) {// in categorias
            	Join<Anuncio, Categoria> categoriaJoin = root.join("categorias", JoinType.INNER);
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

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        
		Page<Anuncio> paginacionAnuncios;

		switch (request.getOrden()) {
		case ApplicationConstants.ORDEN_RECIENTE:
			paginaRequest = PageRequest.of(request.getPagina(), ApplicationConstants.TAMANO_PAGINA,
					Sort.by("fechaCreacion").descending());
			break;
		case ApplicationConstants.ORDEN_ANTIGUOS:
			paginaRequest = PageRequest.of(request.getPagina(), ApplicationConstants.TAMANO_PAGINA,
					Sort.by("fechaCreacion").ascending());
			break;
		case ApplicationConstants.ORDEN_PRECIO_MIN:
			paginaRequest = PageRequest.of(request.getPagina(), ApplicationConstants.TAMANO_PAGINA,
					Sort.by("precio").ascending());
			break;
		case ApplicationConstants.ORDEN_PRECIO_MAX:
			paginaRequest = PageRequest.of(request.getPagina(), ApplicationConstants.TAMANO_PAGINA,
					Sort.by("precio").descending());
			break;
		default:
			break;
		}
		paginacionAnuncios = anunciosRepository.findAll(spec, paginaRequest);
        
        return paginacionAnuncios.map(anuncio -> {
			ResponseAnuncioDto res = modelMapper.map(anuncio, ResponseAnuncioDto.class);
			Optional<Usuario> usuario = usuarioRepository.findById((long) anuncio.getIdUsuario());
			usuario.ifPresent(usr -> res.setApodoCreador(usr.getApodo()));
			return res;
		});
      }
}
