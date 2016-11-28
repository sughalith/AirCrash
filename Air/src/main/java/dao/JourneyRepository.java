package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import AirCrash.Air.Journey;
import mappers.IMapResultSetIntoEntity;
import repositories.IJourneyRepository;
import uow.IUnitOfWork;

public class JourneyRepository extends RepositoryBase<Journey>
implements IJourneyRepository{

    private PreparedStatement getTrack;
    private PreparedStatement getDate;
    private PreparedStatement getTime;
    private PreparedStatement getTickets;
    private PreparedStatement getExpectedDuration;

	public JourneyRepository(Connection connection,
			IMapResultSetIntoEntity<Journey> mapper, IUnitOfWork uow) {
		super(connection,mapper, uow);

		try{
		    getTrack = connection.prepareStatement(getTrackSql());
		    getDate = connection.prepareStatement(getDateSql());
		    getTime = connection.prepareStatement(getTimeSql());
		    getTickets = connection.prepareStatement(getTicketsSql());
		    getExpectedDuration = connection.prepareStatement(getExpectedDurationSql());
        }catch(SQLException e){
		    e.printStackTrace();
        }

	}
	
	@Override
	protected String createTableSql() {
		return "" + "CREATE TABLE journey("
				+ "id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,"
				+ "track varchar(20)," + "date Date" +"time int" +"tickets int" + "expectedDuration int" + ")";
	}

	@Override
	protected String tableName() {
		return "journey";
	}

	protected String insertSql() {
		return "INSERT INTO journey(track, date, time, tickets, expectedDuration) VALUES (?,?,?,?,?)";
	}

	protected String updateSql() {
		return "UPDATE journey SET (track, date, time, tickets, expectedDuration)=(?,?,?,?,?) WHERE id=?";
	}

	protected String getTrackSql(){return "SELECT * FROM journey where track = ?";}
	protected String getDateSql(){return "SELECT * FROM journey where date = ?";}
	protected String getTimeSql(){return "SELECT * FROM journey where time = ?";}
	protected String getTicketsSql(){return "SELECT * FROM journey where tickets = ?";}
	protected String getExpectedDurationSql(){return "SELECT * FROM journey where ExpectedDuration = ?";}


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



    private List<Journey> searchBy(String value){
        List<Journey> journey = new ArrayList<Journey>();
        try{
            getTrack.setString(1,value);
            ResultSet resultSet = getTrack.executeQuery();
            while(resultSet.next()){
                journey.add(mapper.map(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return journey;
    }

    public List<Journey> withTrack(String track) {
        return searchBy(track);
    }

	public List<Journey> withDate(Date Date) {
		return searchBy(Date);
	}

	public List<Journey> withTime(int time) {
		return searchBy(time);
	}

	public List<Journey> withTickets(int tickets) {
		return searchBy(tickets);
	}

	public List<Journey> withExpectedDuration(int expectedDuration) {
		return searchBy(expectedDuration);
	}
}
