package service.event;

import java.sql.SQLException;
import java.util.List;

import dao.event.EventDAO;
import dao.event.EventPostgresDAO;
import models.Event;


/**
 * EventService é responsável pela lógica de negócio relacionada a eventos.
 * Ele interage com o DAO para realizar operações de criação, busca e recuperação de eventos no banco de dados.
 */
public class EventService {
	private EventDAO eventDB;
	
	
	public EventService() {
		this.eventDB = new EventPostgresDAO();
	}
	
	 /**
     * Salva um novo evento no banco de dados.
     * 
     * @param newEvent O evento a ser salvo.
     * @return O evento salvo com o ID gerado pelo banco de dados.
     * @throws SQLException Se ocorrer um erro ao salvar o evento.
     */
	public Event save(Event newEvent) throws SQLException {
		try {
			return this.eventDB.create(newEvent);
		}
		catch (Exception ex) {
			throw ex;
		}
	}
	
	
    /**
     * Retorna todos os eventos do banco de dados.
     * 
     * @return Uma lista de todos os eventos.
     * @throws SQLException Se ocorrer um erro ao buscar os eventos.
     */
	public List<Event> getAll() throws SQLException{
		try {
			return this.eventDB.getAllEvents();
		}
		catch (Exception ex) {
			throw ex;
		}
	}

	 /**
     * Busca eventos no banco de dados pelo nome do evento.
     * 
     * @param eventName O nome do evento para buscar.
     * @return Uma lista de eventos que correspondem ao nome fornecido.
     * @throws SQLException Se ocorrer um erro ao buscar eventos pelo nome.
     */
	public List<Event> getEventsByName(String eventName) throws SQLException {
		try {
			return this.eventDB.getEventsByName(eventName);
		}
		catch (Exception ex) {
			throw ex;
		}
	}
	
	/**
     * Retorna os eventos relacionados a um usuário específico.
     * 
     * @param userId O ID do usuário para buscar eventos relacionados.
     * @return Uma lista de nomes de eventos relacionados ao usuário.
     * @throws SQLException Se ocorrer um erro ao buscar os eventos relacionados ao usuário.
     */
	public List<String> getUserRelatedEvents(int userId) throws SQLException {
		try {
			return this.eventDB.userRelatedEvents(userId);
		}
		catch (SQLException ex) {
			throw ex;
		}
	}

}
