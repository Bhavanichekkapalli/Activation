<html>
<body>
<script src="./jquery-1.11.2.min.js"></script>
<script type="text/javascript">
// Free JavaScript course - coursesweb.net

// create the object with methods to add and delete <option></option>
var adOption = new Object();

  adOption.checkList = function(list, optval) {
    var re = 0;          
    var opts = document.getElementById(list).getElementsByTagName('option');

    for(var i=0; i<opts.length; i++) {
      if(opts[i].value == document.getElementById(optval).value) {
        re = 1;
        break;
      }
    }

    return re;       
   };

  adOption.addOption = function(list, optval) {
    var opt_val = document.getElementById(optval).value;

    if(opt_val.length > 0) {
      if(this.checkList(list, optval) == 0) {
        var myoption = document.createElement('option');
        myoption.value = opt_val;
        myoption.innerHTML = opt_val;
        document.getElementById(list).insertBefore(myoption, document.getElementById(list).firstChild);
        document.getElementById(optval).value = '';         
      }
      else alert('The value "'+opt_val+'" already added');
    }
    else alert('Add a value for option');
  };

  adOption.delOption = function(list, optval) {
    var opt_val = document.getElementById(optval).value;

    if(this.checkList(list, optval) == 1) {
      var opts = document.getElementById(list).getElementsByTagName('option');
      for(var i=0; i<opts.length; i++) {
        if(opts[i].value == opt_val) {
          document.getElementById(list).removeChild(opts[i]);
          break;
        }
      }
    }
    else alert('The value "'+opt_val+'" not exist');
  }

 adOption.selOpt = function(opt, txtbox) { document.getElementById(txtbox).value = opt; }

  
  
  function alertNotification(){
	  if(document.getElementById("alertNotify").value =="Mail"){
		  document.getElementById("email").style.display="";
	  }else{
		  document.getElementById("email").style.display="none";
		  document.getElementById("email").value="";

	  }
  }
  alertNotification();
  
  function selectFileListVal(){
	  var x = document.getElementById('patternList');
	     var val = "";
	     for (var i = 0; i < x.length; i++) {
	         val=val+x[i].value + ",";
	      }
	     document.getElementById('patternListVal').value=val;
	     
	     var x = document.getElementById('fileList');
	     var val = "";
	     for (var i = 0; i < x.length; i++) {
	         val=val+x[i].value + ",";
	      }
	     document.getElementById('fileListVal').value=val;
	     
	     var x = document.getElementById('mailList');
	     var val = "";
	     for (var i = 0; i < x.length; i++) {
	         val=val+x[i].value + ",";
	      }
	     document.getElementById('mailListVal').value=val;
	     if(document.getElementById("alertNotify").value =="Mail"){
	    	 if(document.getElementById('mailListVal').value==''){
	    		 alert("please add Mail Id to List");
	    		 return false;
	    	 }
	     }
	     
	     if(document.getElementById("patternListVal").value ==''){
	    	 alert("please add Search Patterens to List");
    		 return false;
	     }
	     
	     if(document.getElementById("fileDir").value ==''){
	    	 alert("please Enter File Directory Path");
    		 return false;
	     }

	     if(document.getElementById("fileDirChk").checked==false){
	    	 alert(document.getElementById("fileDirChk").checked);
	    	 if( document.getElementById('fileListVal').value==''){
	    		 alert("please Enter File Names  to List");
	    	 return false;
	    	 }
	     }
	     
	     return false;
  }
  
  function fileDirCheck(val){
	  if(document.getElementById("fileDirChk").checked)
		  document.getElementById("fileDir").disabled=false;
	  else
		  document.getElementById("fileDir").disabled=true;
  }
  function loadDownloadfnc(){
	  document.getElementById("logSettings").style.display="none";
	  document.getElementById("loadDownloadfnc").style.display="";
	  document.getElementById("loadMetrics").style.display="none";
  }
  function loadMetrics(){
	  document.getElementById("logSettings").style.display="none";
	  document.getElementById("loadDownloadfnc").style.display="none";
	  document.getElementById("loadMetrics").style.display="";
  }
  
  function loadLogSettings(){
	  document.getElementById("logSettings").style.display="";
	  document.getElementById("loadDownloadfnc").style.display="none";
	  document.getElementById("loadMetrics").style.display="none";
  }
  </script>

<form action ="demo" method="post" name="myform" onsubmit="return selectFileListVal();">
<table border="1" cellspacing="0" cellpadding="5">


<tr ><td><table border="0" style="height:100%"><tr style="height:25%"><td> <a href="loadLogSettings();" > Log Settings</a></td></tr>
<tr style="height:25%"><td> <a href="loadDownloadfnc();">Download Trace File</a></td></tr>
<tr style="height:25%"><td><a href="loadMetrics();">Metrics</a></td></tr></table></td>

<td id="loadDownloadfnc" style="display:none;"> Please <a href="">click here </a> to download Log Trace file</td>
<td id="loadMetrics" style="display:none;"> metrics</td>
<td id="logSettings">
<table>
<input type="hidden" name="patternListVal" id="patternListVal" />
<input type="hidden" name="fileListVal" id="fileListVal" /> 
<input type="hidden" name="mailListVal" id="mailListVal" /> 
<tr>
<td>  Pattern: </td> <td><input type="text" name="optval" id="optval" /></td>
<td><table><tr><td> <input type="button" id="addopt" name="addopt" value=">" onclick="adOption.addOption('patternList', 'optval');" />
</td><tr><td>
  <input type="button" id="del_opt" name="del_opt" value="<" onclick="adOption.delOption('patternList', 'optval');" />
  
</td>
<td>
  <select multiple="true" name="patternList" id="patternList"  onchange="adOption.selOpt(this.value, 'optval')" style="width: 150px; height: 70px; margin: 0px 2px 0px 3px;">></select></td>
 </tr></table></td>
 
</tr>

<tr><td >File Directory</td>
 <td ><input type="text" name="fileDir" id="fileDir"  /></td><td>Directory Search Req  
 <input type="checkbox" name="fileDirChk" id="fileDirChk" /></td>
 </tr>

<tr><td>FileNames</td><td><input type="text"  name="fileName" id="fileName"/></td>
<td><table><tr><td> <input type="button" id="addfile" name="addfile" value=">" onclick="adOption.addOption('fileList', 'fileName');" />
</td><tr><td>
  <input type="button" id="delFile" name="delFile" value="<" onclick="adOption.delOption('fileList', 'fileName');" />
  
</td>
<td>
  <select multiple="true" name="fileList" id="fileList" size="2" onchange="adOption.selOpt(this.value, 'fileName')" style="width: 150px; height: 70px; margin: 0px 2px 0px 3px;">></select></td>
  </tr></table></td></tr>
  
<tr><td> Alert Notification :</td>
<td colspan="2" ><select name="alertNotify"  id="alertNotify" onchange="alertNotification()"><option value="Save to Disk">Save to Disk</option>
<option value="Mail">Mail</option>
</select></td></tr>

<tr style="display:none" id="email"><td  >Email Id </td><td ><input type="text" name="emailId" id="emailId"/></td>

<td><table><tr><td> <input type="button" id="addmail" name="addmail" value=">" onclick="adOption.addOption('mailList', 'emailId');" />
</td><tr><td>
  <input type="button" id="delMail" name="delMail" value="<" onclick="adOption.delOption('mailList', 'emailId');" />
  
</td>
<td>
  <select multiple="true" name="mailList" id="mailList" size="2" onchange="adOption.selOpt(this.value, 'emailId')" style="width: 150px; height: 70px; margin: 0px 2px 0px 3px;">></select></td>
  </tr></table></td>
</tr>
<tr><td> Time Delay :</td><td colspan="3" ><input type="text" name="timeDelay" id="timeDelay"/></td></tr>
<br/>
<tr><td colspan="4"><input type="submit" name="Submit" value="Save Settings" /> </td></tr>

</table></td></tr></table>
</form></body>
</html>