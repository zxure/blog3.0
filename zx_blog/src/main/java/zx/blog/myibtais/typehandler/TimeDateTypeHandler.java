package zx.blog.myibtais.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import zx.blog.util.TimeDateUtil;

public class TimeDateTypeHandler implements TypeHandler<Date>
{

	@Override
	public void setParameter(PreparedStatement ps, int i, Date date, JdbcType jdbcType)
			throws SQLException
	{
		ps.setInt(i, TimeDateUtil.getTimestampFromDate(date));
	}

	@Override
	public Date getResult(ResultSet rs, String columnName) throws SQLException
	{
		return null;
	}

	@Override
	public Date getResult(ResultSet rs, int columnIndex) throws SQLException
	{
		return TimeDateUtil.getDateFromTimestamp(rs.getInt(columnIndex));
	}

	@Override
	public Date getResult(CallableStatement cs, int columnIndex) throws SQLException
	{
		return null;
	}
	
}
