package com.mkyong.web.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mkyong.web.model.Connection;
import com.mkyong.web.model.Journey;
import com.mkyong.web.model.Location;
import com.mkyong.web.model.UserRoute;
import com.mkyong.web.model.Users;

@Repository(value="mainDAO")
public class MainDAO {

	@Autowired
	@Qualifier("mainServiceSessionFactory")
	private SessionFactory sessionFactory;
	
	Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public List<Location> getAllLocations(){
		final Query query = getSession().getNamedQuery(
				Location.GET_ALL_LOCATIONS);
		return query.list();
	}
	
	public Location getLocationByName(String name){
		final Query query = getSession().getNamedQuery(
				Location.GET_LOCATION_BY_NAME).setParameter("name", name);
		return (Location) query.list().get(0);
	}
	
	public List<Connection> getAllConnections(){
		final Query query = getSession().getNamedQuery(
				Connection.GET_ALL_CONNECTIONS);
		return query.list();
	}
	
	public Users getUserByUserName(String userName){
		final Query query = getSession().getNamedQuery(
				Users.GET_USER_USERNAME).setParameter("userName", userName);
		return (Users) query.list().get(0);
	}
	
	public Long saveUserRoute(UserRoute route){
		return (Long) getSession().save(route);
	}
	
	public void saveJourney(Journey journey){
		getSession().save(journey);
	}
	
	public List<UserRoute> getAllUserRoute(){
		final Query query = getSession().getNamedQuery(
				UserRoute.GET_ALL_USERROUTES);
		return query.list();
	}
	
}
