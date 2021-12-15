package model.service;

import java.sql.SQLException;


import model.HBTI;

import model.dao.HBTIDAO;

public class HBTIManager {
	private static HBTIManager hbtiMan = new HBTIManager();

	private HBTIDAO hbtiDAO;
	
	private HBTIManager() {
		try {
			hbtiDAO = new HBTIDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static HBTIManager getInstance() {
		return hbtiMan;
	}
	
	public HBTI findHBTI(int hbti_id) throws SQLException {
		HBTI hbti = hbtiDAO.findHBTI(hbti_id);
		return hbti;
	}
	
	public String findHbtiName(int hbti_id) throws SQLException{
		return hbtiDAO.findHbtiName(hbti_id);
	}
	

	public String findHbtiImg(String name) throws SQLException {
		return hbtiDAO.findHbtiImg(name);
	}
}