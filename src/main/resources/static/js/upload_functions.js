
//'use strict';

var uploadForm = document.querySelector('#uploadForm');
var uploadInput = document.querySelector('#uploadFile');
var uploadError = document.querySelector('#uploadError');
var uploadSuccess = document.querySelector('#uploadSuccess');

let csrfParameterName = document.querySelector("#csrf_parameter_name").value;
let csrfToken = document.querySelector("#csrf_token").value;

function uploadSingleFile(file, description, type, date, childId, schoolClassId)
{
    let formData = new FormData();
    let xhr = new XMLHttpRequest();
    
    console.log("Send photo");
    console.log(description);
    console.log(type);
    console.log(date);
    console.log(childId);
    formData.append("file", file);
    formData.append("description", description);
    formData.append("type", type);
    formData.append("date", date);
    formData.append("childId", childId);
    formData.append("schoolClassId", schoolClassId);
    formData.append(csrfParameterName, csrfToken);
    
    let url = "/upload";
    console.log(url);
    xhr.open("POST", url);
    console.log("Photo posted");
    
    xhr.onload = function() {
        console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        if(xhr.status == 200) {
            uploadError.style.display = "none";
            uploadSuccess.innerHTML = "<p>Photo Uploaded Successfully.</p><p>DownloadUrl : <a href='" 
            	+ response.fileDownloadUri + "' target='_blank'>" 
            	+ response.fileDownloadUri + "</a></p>";
            uploadSuccess.style.display = "block";
        } else {
            uploadSuccess.style.display = "none";
            uploadError.innerHTML = (response && response.message) || "Some Error Occurred";
        }
    }
    xhr.send(formData);
    
}

function onSubmit()
{
    console.log("Add event listener");
    if(uploadInput.files[0]==null)
    {
        console.log("Error : The file is null");
        return null;
    }    
    var file = uploadInput.files[0];
    var description = uploadForm.description.value;
    var type = uploadForm.type.value;
    var date = uploadForm.date.value;
    var childId = uploadForm.childId.value;
    var schoolClassId = uploadForm.schoolClassId.value;
    
    if(file === 0) {
        uploadError.innerHTML = "Please select an Image";
        uploadError.style.display = "block";
    }
    console.log("Call upload");
    uploadSingleFile(file,description, type, date, childId, schoolClassId);
}
