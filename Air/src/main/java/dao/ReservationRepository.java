package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import AirCrash.Air.Reservation;
import mappers.IMapResultSetIntoEntity;
import repositories.IReservationRepository;
import uow.IUnitOfWork;

public class ReservationRepository extends RepositoryBase<Reservation>
implements IReservationRepository{

    private PreparedStatement getJourneyId;
    private PreparedStatement getPrice;
    private PreparedStatement getNumberOfTickets;

	public ReservationRepository(Connection connection,
			IMapResultSetIntoEntity<Reservation> mapper, IUnitOfWork uow) {
		super(connection,mapper, uow);

		try{
		    getJourneyId = connection.prepareStatement(getJourneyIdSql());
		    getPrice = connection.prepareStatement(getPriceSql());
		    getNumberOfTickets = connection.prepareStatement(getNumberOfTicketsSql());
        }catch(SQLException e){
		    e.printStackTrace();
        }

	}
	
	@Override
	protected String createTableSql() {
		return "" + "CREATE TABLE reservation("
				+ "id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,"
				+ "journeyId int," + "price double," +"numberOfTickets int" + ")";
	}

	@Override
	protected String tableName() {
		return "reservation";
	}

	protected String insertSql() {
		return "INSERT INTO reservation(journeyId, price, numberOfTickets) VALUES (?,?,?)";
	}

	protected String updateSql() {
		return "UPDATE reservation SET (journeyId, price, numberOfTickets)=(?,?,?) WHERE id=?";
	}

	protected String getJourneyIdSql(){return "SELECT * FROM reservation where journeyId = ?";}
	protected String getPriceSql(){return "SELECT * FROM reservation where price = ?";}
	protected String getNumberOfTicketsSql(){return "SELECT * FROM reservation where numberOfTickets = ?";}

	@Override
	protected void setUpdate(Reservation entity) throws SQLException {
		update.setInt(1, entity.getJourneyId());
		update.setDouble(2, entity.getPrice());
		update.setInt(3, entity.getNumberOfTickets());
		
	}

	@Override
	protected void setInsert(Reservation entity) throws SQLException {
		insert.setInt(1, entity.getJourneyId());
		insert.setDouble(2, entity.getPrice());
		insert.setInt(3, entity.getNumberOfTickets());
	}



    private List<Reservation> searchBy(int value){
        List<Reservation> reservation = new ArrayList<Reservation>();
        try{
            getJourneyId.setInt(1,value);
            ResultSet resultSet = getJourneyId.executeQuery();
            while(resultSet.next()){
                reservation.add(mapper.map(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return reservation;
    }

    public List<Reservation> withJourneyId(int journeyId) {
        return searchBy(journeyId);
    }

    private List<Reservation> searchBy(double value){
        List<Reservation> reservation = new ArrayList<Reservation>();
        try{
            getJourneyId.setDouble(1,value);
            ResultSet resultSet = getJourneyId.executeQuery();
            while(resultSet.next()){
                reservation.add(mapper.map(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return reservation;
    }
    
	public List<Reservation> withPrice(double price) {
		return searchBy(price);
	}

	public List<Reservation> withNumberOfTickets(int numberOfTickets) {
		return searchBy(numberOfTickets);
	}


}