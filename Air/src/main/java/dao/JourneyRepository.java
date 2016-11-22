package dao;

import java.sql.Connection;
import java.sql.SQLException;

import AirCrash.Air.Journey;
import mappers.IMapResultSetIntoEntity;

public class JourneyRepository extends RepositoryBase<Journey> {

	public JourneyRepository(Connection connection, IMapResultSetIntoEntity<Journey> mapper) {
		super(connection,mapper);
	}
	
	@Override
	protected String createTableSql() {
		return "" + "CREATE TABLE Journey("
				+ "id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,"
				+ "track String," 
				+ "date Date" 
				+ "time int"
				+ "tickets int"
				+ "expectedDuration int"
				+ ")";
	}

	@Override
	protected String tableName() {
		return "Journey";
	}

	protected String insertSql() {
		return "INSERT INTO Journey(track, date, time, tickets, expectedDuration) VALUES (?,?,?,?,?)";
	}

	protected String updateSql() {
		return "UPDATE Journey SET (track, date, time, tickets, expectedDuration)=(?,?,?,?,?) WHERE id=?";
	}


	@Override
	protected void setUpdate(Journey entity) throws SQLException {
		update.setString(1, entity.getTrack());
		update.setDate(2, entity.getDate());
		update.setInt(3, entity.getTime());
		update.setInt(4, entity.getTickets());
		update.setInt(5, entity.getExpectedDuration());
		
	}

	@Override
	protected void setInsert(Journey entity) throws SQLException {
		insert.setString(1, entity.getTrack());
		insert.setDate(2, entity.getDate());
		insert.setInt(3, entity.getTime());
		insert.setInt(4, entity.getTickets());
		insert.setInt(5, entity.getExpectedDuration());
	}
	
}