package ru.alxstn.carsharing.data.company;

import ru.alxstn.carsharing.data.database.BasicTable;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl extends BasicTable implements CompanyDao {

    public static final String COMPANY_TABLE_NAME = "company";
    public static final String COMPANY_COLUMN_ID = "id";
    public static final String COMPANY_COLUMN_NAME = "name";

    final private static String CREATE_COMPANY_TABLE =
            "CREATE TABLE IF NOT EXISTS " + COMPANY_TABLE_NAME + " (" +
                    COMPANY_COLUMN_ID + " INT PRIMARY KEY AUTO_INCREMENT, " +
                    COMPANY_COLUMN_NAME + " VARCHAR(255) NOT NULL, " +
                    "UNIQUE(" + COMPANY_COLUMN_NAME + ")" +
                    ");";

    final private static String INSERT_COMPANY =
            "INSERT INTO %s (%s) VALUES ('%s');";

    final private static String SELECT_ALL =
            "SELECT * FROM " + COMPANY_TABLE_NAME + " ORDER BY " + COMPANY_COLUMN_ID + " ASC;";

    // Param: Int companyId
    final private static String SELECT_COMPANY_NAME_BY_ID =
            "SELECT " + COMPANY_COLUMN_NAME +
                    " FROM " + COMPANY_TABLE_NAME +
                    " WHERE (" + COMPANY_COLUMN_ID + " = %s" + ");";

    public CompanyDaoImpl() {
        super(COMPANY_TABLE_NAME);
        //dropTableIfExists();
        createTable();
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> result = new ArrayList<>();
        try {
            ResultSet companies = dbManager
                    .getConnection()
                    .createStatement()
                    .executeQuery(SELECT_ALL);
            while (companies.next()) {
                result.add(new Company(
                        companies.getInt(COMPANY_COLUMN_ID),
                        companies.getString(COMPANY_COLUMN_NAME)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String buildInsertNewCompanyQuery(Company company) {
        return String.format(INSERT_COMPANY, COMPANY_TABLE_NAME, COMPANY_COLUMN_NAME, company.getName());
    }

    @Override
    public void addNewCompany(Company company) {
        dbManager.executeUpdate(buildInsertNewCompanyQuery(company));
    }

    @Override
    public String getCompanyNameById(int companyId) {
        String companyName = "";
        ResultSet result = null;
        try {
            result = dbManager
                    .getConnection()
                    .createStatement()
                    .executeQuery(String.format(SELECT_COMPANY_NAME_BY_ID, companyId));
            if (result.next()) {
               companyName = result.getString(COMPANY_COLUMN_NAME);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return companyName;
    }

    private boolean createTable() {
        return dbManager.execute(CREATE_COMPANY_TABLE);
    }
}

