
//'use strict';

var uploadForm = document.querySelector('#uploadForm');
var uploadInput = document.querySelector('#uploadFile');
var uploadError = document.querySelector('#uploadError');
var uploadSuccess = document.querySelector('#uploadSuccess');

let csrfParameterName = document.querySelector("#csrf_parameter_name").value;
let csrfToken = document.querySelector("#csrf_token").value;

function uploadSingleFile(file, description, type, date, childsId, schoolClassId)
{
    let formData = new FormData();
    let xhr = new XMLHttpRequest();
    
    console.log("Send photo");
    console.log(childsId);
    formData.append("file", file);
    formData.append("description", description);
    formData.append("type", type);
    formData.append("date", date);
    formData.append("childsId", childsId);
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
            	+ response.downloadUri + "' target='_blank'>" 
            	+ response.name + "</a></p>";
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
    var childsId = "";
    
    var checkboxes = document.getElementsByName('capturedChild');
    console.log("nb children :" + checkboxes.length);
    
    
    for (var i=0, n=checkboxes.length;i<n;i++) 
    {
        if (checkboxes[i].checked) 
        {
        	childsId += ","+checkboxes[i].value;
        }
    }
    if (childsId) 
    	childsId = childsId.substring(1);
    
    var schoolClassId = "-1";
    
    var classes = document.getElementsByName('schoolClassId');
    console.log("nb schoolClasses = " + classes.length);
    
    
    for (var i=0, n=classes.length;i<n;i++) 
    {
        if (classes[i].selected) 
        {
        	schoolClassId = classes[i].value;
        }
    }
    console.log("schoolClassID = " + schoolClassId);
    if(file === 0) {
        uploadError.innerHTML = "Please select an Image";
        uploadError.style.display = "block";
    }
    console.log("Call upload");
    uploadSingleFile(file,description, type, date, childsId, schoolClassId);
}
