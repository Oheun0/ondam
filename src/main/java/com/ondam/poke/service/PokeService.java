package com.ondam.poke.service;

import java.util.Vector;

import com.ondam.poke.dao.PokeDAO;
import com.ondam.poke.dto.PokeDTO;

public class PokeService {

	private PokeDAO dao;

	public PokeService() {
		this.dao = new PokeDAO();
	}

	public Vector<PokeDTO> getPokeList() {
		return dao.getPoke();
	}

	public boolean createPoke(PokeDTO dto) {
		return dao.insertPoke(dto);
	}

	public boolean modifyPoke(PokeDTO dto, int pokeNo) {
		return dao.updatePoke(dto, pokeNo);
	}

	public boolean removePoke(int pokeNo) {
		return dao.deletePoke(pokeNo);
	}
}

