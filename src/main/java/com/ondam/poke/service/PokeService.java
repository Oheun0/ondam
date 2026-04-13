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
	
	public Vector<PokeDTO> getReceivedPokeList(int receiverNo) {
	    return dao.getByReceiverNo(receiverNo);
	}

	public Vector<PokeDTO> getSentPokeList(int senderNo) {
	    return dao.getBySenderNo(senderNo);
	}

	public PokeDTO getPokeById(int pokeNo) {
	    return dao.getPokeById(pokeNo);
	}

	public boolean updateSendState(int pokeNo, int sendState) {
	    return dao.updateSendState(pokeNo, sendState);
	}
	
	public int createPokeAndGetNo(PokeDTO dto) {
	    return dao.insertPokeAndGetNo(dto);
	}
	
	public Vector<PokeDTO> getPokesFromSender(int receiverNo, int senderNo) {
	    return dao.getBySenderAndReceiver(receiverNo, senderNo);
	}
}