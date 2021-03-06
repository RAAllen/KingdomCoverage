package com.skilldistillery.mvckingdomcoverage.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.skilldistillery.kingdomcoverage.entities.Insured;
import com.skilldistillery.kingdomcoverage.entities.Occupation;

@Transactional
@Component
public class OccupationDAOImpl implements OccupationDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override 
	public Occupation show(int id) {
		return em.find(Occupation.class, id);
	}
	
	@Override
	public Occupation create(Occupation occupation) {
		em.persist(occupation);
		em.flush();
		return occupation;
	}

	@Override
	public Occupation update(int id, Occupation occupation) {
		Occupation managed = em.find(Occupation.class, id);
		managed.setName(occupation.getName());
		managed.setClients(occupation.getClients());
		managed.setCostMultiplier(occupation.getCostMultiplier());
		return managed;
	}
	
	@Override
	public List<Insured> clientsShow(int id) {
		String query = "SELECT o.clients FROM Occupation o WHERE o.id = :id";
		List<Insured> messages = em.createQuery(query, Insured.class).setParameter("id", id).getResultList();	
		return messages;
	}
	
	@Override
	public List<Occupation> getAllOccupations() {
		List<Occupation> jobs = new ArrayList<>();
		String query = "SELECT o FROM Occupation o";
		jobs = em.createQuery(query, Occupation.class)
				.getResultList();
		return jobs;
	}
}
