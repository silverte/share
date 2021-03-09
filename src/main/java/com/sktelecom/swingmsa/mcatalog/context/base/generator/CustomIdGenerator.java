package com.sktelecom.swingmsa.mcatalog.context.base.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import com.sktelecom.swingmsa.mcatalog.context.base.exception.BizException;

public class CustomIdGenerator implements IdentifierGenerator, Configurable {
	
	public static final String PARAM_TBL_NM = "tblNm";
	public static final String PARAM_ID_COL = "idCol";
	public static final String PARAM_RANGE  = "range";
	
	private String tblNm;
	private String idCol;
	private long range;

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		
		//String sql = "select max(" + idCol + ") + 1 from "+ tblNm + " where " + idCol + " > " + range; 20200511
		String sql = "select max(to_number(" + idCol + ")) + 1 from "+ tblNm + " where to_number(" + idCol + ") > " + range;
		
		Connection conn = session.connection();
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				/*20200511
				 * long id = rs.getLong(1);
				if(id == 0) {
					id = range + 1;
				}*/				
//				String id = rs.getString(1);
				String id = String.valueOf(rs.getLong(1));
				if(id == null) {
					id = String.valueOf(range + 1);
				}
				return id;
			}
		} catch(SQLException se) {
			throw new BizException(se);
		}
		
		return null;
	}

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		
		this.tblNm = ConfigurationHelper.getString(PARAM_TBL_NM, params);
		this.idCol = ConfigurationHelper.getString(PARAM_ID_COL, params);
		this.range = Long.parseLong(ConfigurationHelper.getString(PARAM_RANGE, params));
	}

}
