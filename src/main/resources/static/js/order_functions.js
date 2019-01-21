/**
 * 
 */
//'use strict';

var orderForm = document.querySelector('#orderForm');
var orderError = document.querySelector('#orderError');
var orderSuccess = document.querySelector('#orderSuccess');
var displayArea = document.querySelector('#displayArea');

//let csrfParameterName = document.querySelector("#csrf_parameter_name").value;
//let csrfToken = document.querySelector("#csrf_token").value;

$(document).ready(function () {
	let xhr = new XMLHttpRequest();
	let url = "/display";

    xhr.open("GET", url);
    xhr.send();
    console.log("Request sent");
    xhr.onload = function() {
	    var response = JSON.parse(xhr.responseText);
	    
	    if(xhr.status == 200) {
	    	console.log("Request OK");
	    	console.log("response = " + response + "(" + response.size + ")");
	    	
	    	for (var i=0, n=response.length;i<n;i++) 
	        {
	            displayPhoto(response[i]);
	        }
	    	
	    } else {
	    	console.log("Request ERROR");
	    }
    }
 });


function displayPhoto(photo)
{
	displayArea.innerHTML +=
		"<tr data-status=\"" + photo.type +"\">" +
			"<td><div class=\"ckbox\">" +
			"<input type=\"checkbox\" name = \"checkPhoto\" id=" + photo.id +">" +
			"<label for=" + photo.id + "></label>" +
			"</div></td>" +
			"<td><a href=\"javascript:;\" class=\"star\"><i class=\"glyphicon glyphicon-star\"></i></a> </td>"+
			"<td><div class=\"media\"><a href=\"#\" class=\"pull-left\">" +
				"<img src=\"" + photo.downloadUri + "\" class=\"media-photo\"></a>" +
				"<div class=\"media-body\">" +
					"<span class=\"media-meta pull-right\">" + photo.date + "</span>" +
					"<h4 class=\"title\">Description<span class=\"pull-right "+photo.type+"\">("+photo.type+")</span></h4>" +
					"<p class=\"summary\">"+ photo.description + "</p>" +
		"</div></div></td></tr>";
	displayArea.style.display = "block";
}

function displayGroupPhoto(photo)
{
	
}

function displayNotTaggedPhoto(photo)
{
	
}

function executeOrder(photosId)
{
    let formData = new FormData();
    let xhr = new XMLHttpRequest();
   
    formData.append("photosId", photosId);
    let url = "/execute_order";
    xhr.open("POST", url);
    
    xhr.onload = function() {
        console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        if(xhr.status == 200) {
            orderError.style.display = "none";
            orderSuccess.innerHTML = "<p>Order Successfully.</p>";
            orderSuccess.style.display = "block";
        } else {
            orderSuccess.style.display = "none";
            orderError.innerHTML = (response && response.message) || "Some Error Occurred";
        }
    }
    xhr.send(formData);
}

function onSubmit()
{
    var photosId = "";
    
    var checkboxes = document.getElementsByName('checkPhoto');
    console.log("nb photos :" + checkboxes.length);
    
    
    for (var i=0, n=checkboxes.length;i<n;i++) 
    {
        if (checkboxes[i].checked) 
        {
        	photosId += ","+checkboxes[i].value;
        }
    }
    if (photosId) 
    	photosId = photosId.substring(1);
    
    console.log("order");
    executeOrder(photosId);
}
