const editButtonPress = function(button)
{
    let id_row = button.id.split("button_")[1];
    let departmentName = document.querySelector("#row_"+id_row+" .departmentNameField").innerHTML;
    let managerId = document.querySelector("#row_"+id_row+" .managerIdField").innerHTML;
    let locationCity = document.querySelector("#row_"+id_row+" .locationCityField").innerHTML;
    let locationId = document.querySelector("#row_"+id_row+" .locationIdField").innerHTML;

    document.querySelector("#formEditionId").value = id_row;
    document.querySelector("#formDepartmentName").value = departmentName;
    document.querySelector("#formManagerId").value = managerId;
    document.querySelector("#formLocationId").value = locationId;
}