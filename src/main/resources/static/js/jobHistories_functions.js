const editButtonPress = function(button)
{
    let id_row = button.id.split("button_")[1];
    let employeeId = document.querySelector("#row_"+id_row+" .employeeIdField").innerHTML;
    let startDate = document.querySelector("#row_"+id_row+" .startDateField").innerHTML;
    let endDate = document.querySelector("#row_"+id_row+" .endDateField").innerHTML;
    let departmentId = document.querySelector("#row_"+id_row+" .departmentIdField").innerHTML;
    let departmentName = document.querySelector("#row_"+id_row+" .departmentNameField").innerHTML;
    let jobId = document.querySelector("#row_"+id_row+" .jobIdField").innerHTML;
    let jobTitle = document.querySelector("#row_"+id_row+" .jobTitleField").innerHTML;

    document.querySelector("#formEmployeeId").value = employeeId;
    document.querySelector("#formStartDate").value = startDate;
    document.querySelector("#formEndDate").value = endDate;
    document.querySelector("#formDepartmentId").value = departmentId;
    document.querySelector("#formJobId").value = jobId;
}