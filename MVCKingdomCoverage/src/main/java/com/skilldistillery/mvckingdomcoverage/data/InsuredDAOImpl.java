package com.skilldistillery.mvckingdomcoverage.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.skilldistillery.kingdomcoverage.entities.Address;
import com.skilldistillery.kingdomcoverage.entities.Agent;
import com.skilldistillery.kingdomcoverage.entities.CoverageType;
import com.skilldistillery.kingdomcoverage.entities.InsurancePlan;
import com.skilldistillery.kingdomcoverage.entities.Insured;
import com.skilldistillery.kingdomcoverage.entities.InsuredAddressDTO;
import com.skilldistillery.kingdomcoverage.entities.Message;
import com.skilldistillery.kingdomcoverage.entities.User;
import com.skilldistillery.kingdomcoverage.entities.UserInsuredAddressDTO;

@Transactional
@Component
public class InsuredDAOImpl implements InsuredDAO {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private SpeciesDAO sdao = new SpeciesDAOImpl();

	@Autowired
	private OccupationDAO odao = new OccupationDAOImpl();

	@Override
	public Insured show(int id) {
		return em.find(Insured.class, id);
	}

	@Override
	public Insured create(Insured insured) {
		User u = new User();
		u.setName(insured.getfName());
		em.persist(insured);
		em.flush();
		return insured;
	}

	@Override
	public Insured update(int id, Insured insured) {
		Insured managed = em.find(Insured.class, id);
		managed.setAge(insured.getAge());
		managed.setGender(insured.getGender());
		managed.setfName(insured.getfName());
		managed.setlName(insured.getlName());
		managed.setSpecies(insured.getSpecies());
		managed.setOccupation(insured.getOccupation());
		managed.setMessages(insured.getMessages());
		managed.setAddress(insured.getAddress());
		managed.setPlans(insured.getPlans());
		return managed;
	}

	@Override
	public Insured updateInsured(InsuredAddressDTO dto, Address address, Insured insured) {
		address.setStreet(dto.getAddressStreet());
		address.setCity(dto.getAddressCity());
		address.setRealm(dto.getAddressRealm());

		insured.setfName(dto.getInsuredFirstName());
		insured.setlName(dto.getInsuredLastName());
		insured.setAge(dto.getInsuredAge());
		insured.setGender(dto.getInsuredGender());
		insured.setSpecies(sdao.showSpecies(dto.getInsuredSpeciesId()));
		insured.setOccupation(odao.show(dto.getInsuredOccupationId()));

		Insured managed = em.find(Insured.class, insured.getId());
		managed.setAge(insured.getAge());
		managed.setGender(insured.getGender());
		managed.setfName(insured.getfName());
		managed.setlName(insured.getlName());
		managed.setSpecies(insured.getSpecies());
		managed.setOccupation(insured.getOccupation());
		managed.setMessages(insured.getMessages());
		managed.setAddress(insured.getAddress());
		managed.setPlans(insured.getPlans());
		return managed;
	}

	@Override
	public Address getAddressByInsuredId(int id) {
		Address address = new Address();
		String query = "SELECT i FROM Insured i JOIN FETCH i.address where i.id = :id";
		List<Insured> insured = em.createQuery(query, Insured.class).setParameter("id", id).getResultList();
		if (insured.size() > 0) {
			address = insured.get(0).getAddress();
		}
		return address;
	}

	@Override
	public List<Agent> getAgentsByInsuredId(Integer id) {
		List<Agent> agents = new ArrayList<>();
		String query = "SELECT i from Insured i JOIN FETCH i.agents where i.id = :id";
		List<Insured> insured = em.createQuery(query, Insured.class).setParameter("id", id).getResultList();
		if (insured.size() != 0) {
			agents = insured.get(0).getAgents();
		}
		return agents;
	}

	@Override
	public List<Message> getMessagesByInsuredId(Integer id) {
		List<Message> messages = new ArrayList<>();
		String query = "SELECT i from Insured i JOIN FETCH i.messages where i.id = :id";
		List<Insured> insured = em.createQuery(query, Insured.class).setParameter("id", id).getResultList();
		if (insured.size() != 0)
			messages = insured.get(0).getMessages();
		return messages;
	}

	@Override
	public Message getNewestInboxMessagesByInsuredId(Integer id) {
		List<Message> messages = new ArrayList<>();
		Message newestInbox = new Message();
		newestInbox.setId(-1);
		Insured insured = em.find(Insured.class, id);
		if(insured.getMessages().size() > 0) {
			messages = insured.getMessages();
		}
		if (messages.size() != 0) {
			for (int i = 0; i < messages.size(); i++) {
				if (messages.get(i).toString().equals("n") && messages.get(i).getId() > newestInbox.getId()) {
					newestInbox = messages.get(i);
				}
			}
		}
		else {
			newestInbox = null;
		}
		return newestInbox;
	}
	
	@Override
	public Message getNewestSentMessagesByInsuredId(Integer id) {
		List<Message> messages = new ArrayList<>();
		Message newestInbox = new Message();
		newestInbox.setId(-1);
		Insured insured = em.find(Insured.class, id);
		if(insured.getMessages().size() > 0) {
			messages = insured.getMessages();
		}
		if (messages.size() != 0) {
			for (int i = 0; i < messages.size(); i++) {
				if (messages.get(i).toString().equals("y") && messages.get(i).getId() > newestInbox.getId()) {
					newestInbox = messages.get(i);
				}
			}
		}
		else {
			newestInbox = null;
		}
		return newestInbox;
	}

	@Override
	public Integer getInsuredIdByUserId(Integer id) {
		String query = "SELECT i From Insured i where i.user.id = :id";
		List<Insured> insured = em.createQuery(query, Insured.class).setParameter("id", id).getResultList();
		return insured.get(0).getId();
	}

	@Override
	public List<Message> inboxShow(int id) {
		String query = "SELECT i.messages FROM Insured i WHERE i.id = :id";
		List<Message> messages = em.createQuery(query, Message.class).setParameter("id", id).getResultList();
		return messages;
	}

	@Override
	public List<InsurancePlan> listPlans(int id) {
		String query = "SELECT i FROM InsurancePlan i WHERE i.insured.id = :id";
		List<InsurancePlan> plans = em.createQuery(query, InsurancePlan.class).setParameter("id", id).getResultList();
		return plans;
	}

	@Override
	public Insured createUserAndInsuredAndAddress(UserInsuredAddressDTO dto) {
		User user = new User();
		user.setName(dto.getUserName());
		user.setPassword(dto.getUserPassword());

		Address address = new Address();
		address.setStreet(dto.getAddressStreet());
		address.setCity(dto.getAddressCity());
		address.setRealm(dto.getAddressRealm());

		Insured insured = new Insured();
		insured.setfName(dto.getInsuredFirstName());
		insured.setlName(dto.getInsuredLastName());
		insured.setAge(dto.getInsuredAge());
		insured.setGender(dto.getInsuredGender());
		insured.setSpecies(sdao.showSpecies(dto.getInsuredSpeciesId()));
		insured.setOccupation(odao.show(dto.getInsuredOccupationId()));
		insured.setUser(user);
		insured.setAddress(address);

		em.persist(insured);
		em.flush();
		return insured;
	}

	@Override
	public List<CoverageType> getCoveragesByInsuredId(int id) {
		List<CoverageType> coverages = new ArrayList<>();
		String query = "SELECT p FROM InsurancePlan p JOIN FETCH p.coverages WHERE p.insured.id = :id";
		List<InsurancePlan> plans = em.createQuery(query, InsurancePlan.class).setParameter("id", id).getResultList();

		if (plans.size() != 0) {
			coverages = plans.get(0).getCoverages();
			return coverages;
		}

		return null;
	}
}
