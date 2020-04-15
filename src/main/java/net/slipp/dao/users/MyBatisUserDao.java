package net.slipp.dao.users;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import net.slipp.domain.users.User;

public class MyBatisUserDao implements UserDao {
	private static final Logger log = LoggerFactory.getLogger(MyBatisUserDao.class);

	private SqlSession session;
	private DataSource dataSource;
	
	public void setSession(SqlSession session) {
		this.session = session;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@PostConstruct
	public void initialize() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("slipp.sql"));
		DatabasePopulatorUtils.execute(populator, dataSource);
		log.info("database initialized success!");
	}
	
	@Override
	public User findById(String userId) {
		return session.selectOne("UserMapper.findById", userId);
	}

	@Override
	public void create(User user) {
		session.insert("UserMapper.create", user);
	}

	@Override
	public void update(User user) {
		session.update("UserMapper.update", user);
	}

}
