package dao;

import java.util.List;

import model.Device;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class DeviceDao {

	private final Session session;

	public DeviceDao(Session session) {
		this.session = session;
	}

	public void save(Device device) {
		session.save(device);
	}
	
	public boolean contains(String hash) {
		List<Device> list = this.session.createCriteria(Device.class).add(Restrictions.eq("registrationId", hash)).list();
		return !list.isEmpty();
	}
}
