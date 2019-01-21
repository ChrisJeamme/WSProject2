const editButtonPress = function(button)
{
    let id_row = button.id.split("button_")[1];
    let streetAddress = document.querySelector("#row_"+id_row+" .streetAddressField").innerHTML;
    let postalCode = document.querySelector("#row_"+id_row+" .postalCodeField").innerHTML;
    let city = document.querySelector("#row_"+id_row+" .cityField").innerHTML;
    let stateProvince = document.querySelector("#row_"+id_row+" .stateProvinceField").innerHTML;
    let countryId = document.querySelector("#row_"+id_row+" .countryIdField").innerHTML;

    document.querySelector("#formEditionId").value = id_row;
    document.querySelector("#formStreetAddress").value = streetAddress;
    document.querySelector("#formPostalCode").value = postalCode;
    document.querySelector("#formCity").value = city;
    document.querySelector("#formStateProvince").value = stateProvince;
    document.querySelector("#formCountryId").value = countryId;
}