const editButtonPress = function(button)
{
    let id_row = button.id.split("button_")[1];
    let jobTitle = document.querySelector("#row_"+id_row+" .jobTitleField").innerHTML;
    let minSalary = document.querySelector("#row_"+id_row+" .minSalaryField").innerHTML;
    let maxSalary = document.querySelector("#row_"+id_row+" .maxSalaryField").innerHTML;

    document.querySelector("#formEditionId").value = id_row;
    document.querySelector("#formJobTitle").value = jobTitle;
    document.querySelector("#formMinSalary").value = minSalary;
    document.querySelector("#formMaxSalary").value = maxSalary;
}