package com.company.demo.bean;

import com.company.demo.entity.CustomerGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class CustomerJdbcService {

    // tag::stored-procedure[]
    // tag::jdbc-template[]
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // end::jdbc-template[]
    // end::stored-procedure[]

    // tag::jdbc-client[]
    @Autowired
    private JdbcClient jdbcClient;

    // end::jdbc-client[]

    // tag::jdbc-template[]
    public Map<String, BigDecimal> getCustomerAmounts(CustomerGrade grade) {
        return jdbcTemplate.query(
                """
                select c.NAME, sum(o.AMOUNT)
                from CUSTOMER c join ORDER_ o on c.ID = o.CUSTOMER_ID
                where c.GRADE = ?
                group by c.NAME
                """,
                (ResultSet rs) -> {
                    Map<String, BigDecimal> result = new HashMap<>();
                    while (rs.next()) {
                        result.put(rs.getString(1), rs.getBigDecimal(2));
                    }
                    return result;
                },
                grade.getId()
        );
    }
    // end::jdbc-template[]

    // tag::jdbc-client[]
    public Map<String, BigDecimal> getCustomerAmountsByJdbcClient(CustomerGrade grade) {
        return jdbcClient.sql("""
                    select c.NAME, sum(o.AMOUNT)
                    from CUSTOMER c join ORDER_ o on c.ID = o.CUSTOMER_ID
                    where c.GRADE = :grade
                    group by c.NAME
                    """)
                .param("grade", grade.getId())
                .query((ResultSet rs) -> {
                    Map<String, BigDecimal> result = new HashMap<>();
                    while (rs.next()) {
                        result.put(rs.getString(1), rs.getBigDecimal(2));
                    }
                    return result;
                });
    }
    // end::jdbc-client[]

    // tag::stored-procedure[]
    public record CustomerStats(Integer totalOrders, BigDecimal totalAmount) {
    }

    public CustomerStats callStoredProcedure(UUID customerId) {
        // Using SimpleJdbcCall for stored procedure
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName("get_customer_stats")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_customer_id", Types.OTHER), // UUID type
                        new SqlOutParameter("total_orders", Types.INTEGER),
                        new SqlOutParameter("total_amount", Types.DECIMAL)
                );

        // Execute the stored procedure
        Map<String, Object> result = jdbcCall.execute(customerId);

        // Extract results
        Integer totalOrders = (Integer) result.get("total_orders");
        BigDecimal totalAmount = (BigDecimal) result.get("total_amount");

        return new CustomerStats(totalOrders, totalAmount);
    }
    // end::stored-procedure[]

}
