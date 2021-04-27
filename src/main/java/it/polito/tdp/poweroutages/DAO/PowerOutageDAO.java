package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	
	public List<PowerOutage> getNercPowerOutage(Nerc n){
		
		String sql = "SELECT id, date_event_began, date_event_finished, customers_affected "
				+ "FROM poweroutages "
				+ "WHERE nerc_id = ?";
		
		List<PowerOutage> pws = new ArrayList<PowerOutage>();
		
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n.getId());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {				
	
				PowerOutage pw = new PowerOutage(rs.getInt("id"),  rs.getInt("customers_affected"), 
						rs.getTimestamp("date_event_began").toLocalDateTime(), rs.getTimestamp("date_event_finished").toLocalDateTime());
				
				pws.add(pw);
			}
			
			conn.close();
		
			return pws;
		
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
