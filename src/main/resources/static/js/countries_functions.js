const editButtonPress = function(button)
{
    let id_row = button.id.split("button_")[1];
    let countryName = document.querySelector("#row_"+id_row+" .countryNameField").innerHTML;
    let regionName = document.querySelector("#row_"+id_row+" .regionNameField").innerHTML;
    let regionId = document.querySelector("#row_"+id_row+" .regionIdField").innerHTML;

    document.querySelector("#formEditionId").value = id_row;
    document.querySelector("#formCountryName").value = countryName;
    document.querySelector("#formRegionId").value = regionId;
}