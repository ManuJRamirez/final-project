package com.wallaclone.finalproject.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallaclone.finalproject.dto.PruebaDto;
import com.wallaclone.finalproject.repository.PruebaRepository;
import com.wallaclone.finalproject.service.SaludoService;

@Service
public class SaludoServiceImpl implements SaludoService {
	
	@Autowired
	PruebaRepository pruebaRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<PruebaDto> getSaludo() {
		return pruebaRepository.findAll().stream().map(prueba -> modelMapper.map(prueba, PruebaDto.class)).collect(Collectors.toList());
	}
	
}
