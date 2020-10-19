package dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import entity.domain.Member;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class MemberDao {

	private JdbcTemplate jdbcTemplate;
	private RowMapper<Member> memberRowMapper = new RowMapper<Member>() {
		@Override
		public Member mapRow(ResultSet resultSet, int i) throws SQLException {
			Member member = new Member(resultSet.getString("EMAIL"),
					resultSet.getString("PASSWORD"),
					resultSet.getString("NAME"),
					resultSet.getTimestamp("REGDATE"));
			member.setId(resultSet.getLong("ID"));
			return member;
		}
	};

	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Member selectByEmail(String email) {
		List<Member> results = jdbcTemplate.query(
				"select * from MEMBER where EMAIL = ?",
				memberRowMapper, email);
		return results.isEmpty()? null : results.get(0);
	}

	public Member selectById(Long id) {
		List<Member> results = jdbcTemplate.query(
				"select * from MEMBER where id = ?",
				memberRowMapper, id);

		return results.isEmpty()? null : results.get(0);
	}

	public void insert(Member member) {
		KeyHolder keyHolder = new GeneratedKeyHolder(); // 자동 생성된 키 값을 구해주는 KeyHolder 구현체.
		jdbcTemplate.update(new PreparedStatementCreator() { // PreparedStatementCreator와 KeyHolder를 파라미터로 갖는 update 메서드.
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(
						"insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE)" +
								"values(?, ?, ?, ?)",
						new String[] {"ID"} // 자동생성되는 키 칼럼 목록을 지정할 때 사용
				);
				preparedStatement.setString(1, member.getEmail());
				preparedStatement.setString(2, member.getPassword());
				preparedStatement.setString(3, member.getName());
				preparedStatement.setTimestamp(4, new Timestamp(member.getRegisterDate().getTime()));
				return preparedStatement;
			}
		}, keyHolder);
		Number keyValue = keyHolder.getKey();
		member.setId(keyValue.longValue());
	}

	public void update(Member member) {
		jdbcTemplate.update(
				"update MEMBER set NAME=?, PASSWORD=? where EMAIL=?",
				member.getName(), member.getPassword(), member.getEmail()
		);
	}

	public List<Member> selectAll() {
		List<Member> results = jdbcTemplate.query(
				"select * from MEMBER",
				memberRowMapper
				);
		return results;
	}

	public List<Member> selectByRegdate(java.util.Date from, java.util.Date to) {
		List<Member> results = jdbcTemplate.query(
				"select * from Member where REGDATE between ? and ?" +
						"order by REGDATE desc",
				memberRowMapper,
				from, to
		);
		return results;
	}

	public int count() {
		Integer count = jdbcTemplate.queryForObject("select count(*) from MEMBER", Integer.class);
		return count;
	}

}
