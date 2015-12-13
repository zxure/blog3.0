package zx.blog.myibtais.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import zx.blog.util.TimeDateUtil;

public class TimeDateTypeHandler implements TypeHandler<String>
{

	@Override
	public void setParameter(PreparedStatement ps, int i, String dateStr, JdbcType jdbcType)
			throws SQLException
	{
		ps.setInt(i, TimeDateUtil.getTimestampFromTimestr(dateStr));
	}

	@Override
	public String getResult(ResultSet rs, String columnName) throws SQLException
	{
		return TimeDateUtil.formatTime(rs.getInt(columnName));
	}

	@Override
	public String getResult(ResultSet rs, int columnIndex) throws SQLException
	{
		return TimeDateUtil.formatTime(rs.getInt(columnIndex));
	}

	@Override
	public String getResult(CallableStatement cs, int columnIndex) throws SQLException
	{
		return null;
	}
	
}
