const editButtonPress = function(button)
{
    let id_row = button.id.split("button_")[1];
    let firstName = document.querySelector("#row_"+id_row+" .firstNameField").innerHTML;
    let lastName = document.querySelector("#row_"+id_row+" .lastNameField").innerHTML;
    let commissionPct = document.querySelector("#row_"+id_row+" .commissionPctField").innerHTML;
    let salary = document.querySelector("#row_"+id_row+" .salaryField").innerHTML;
    let email = document.querySelector("#row_"+id_row+" .emailField").innerHTML;
    let managerId = document.querySelector("#row_"+id_row+" .managerIdField").innerHTML;
    let hireDate = document.querySelector("#row_"+id_row+" .hireDateField").innerHTML;
    let phoneNumber = document.querySelector("#row_"+id_row+" .phoneNumberField").innerHTML;
    let departmentId = document.querySelector("#row_"+id_row+" .departmentIdField").innerHTML;
    let departmentName = document.querySelector("#row_"+id_row+" .departmentNameField").innerHTML;
    let jobId = document.querySelector("#row_"+id_row+" .jobIdField").innerHTML;
    let jobTitle = document.querySelector("#row_"+id_row+" .jobTitleField").innerHTML;

    document.querySelector("#formEditionId").value = id_row;
    document.querySelector("#formEmployeeFirstName").value = firstName;
    document.querySelector("#formEmployeeLastName").value = lastName;
    document.querySelector("#formEmployeeCommissionPct").value = commissionPct;
    document.querySelector("#formEmployeeSalary").value = salary;
    document.querySelector("#formEmployeeEmail").value = email;
    document.querySelector("#formEmployeeManagerId").value = managerId;
    document.querySelector("#formEmployeeHireDate").value = hireDate;
    document.querySelector("#formEmployeePhoneNumber").value = phoneNumber;
    document.querySelector("#formDepartementId").value = departmentId;
    document.querySelector("#formJobId").value = jobId;
}