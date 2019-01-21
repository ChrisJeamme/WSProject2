const editButtonPress = function(button)
{
    let id_row = button.id.split("button_")[1];
    let regionName = document.querySelector("#row_"+id_row+" .regionNameField").innerHTML;

    document.querySelector("#formEditionId").value = id_row;
    document.querySelector("#formRegionName").value = regionName;
}