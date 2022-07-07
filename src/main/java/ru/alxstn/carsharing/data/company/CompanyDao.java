package ru.alxstn.carsharing.data.company;

// Data Access Object Pattern (DAO)
// https://www.tutorialspoint.com/design_pattern/data_access_object_pattern.htm

import java.util.List;

public interface CompanyDao {
    List<Company> getAllCompanies();
    void addNewCompany(Company company);

    String getCompanyNameById(int id);
}
