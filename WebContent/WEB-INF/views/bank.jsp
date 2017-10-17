 <script type="text/javascript">
 function bankTerms (obj) {debugger;
            	document.getElementById("bankTerms").style.display  = obj;
            }
 </script>           
<script type="text/javascript" src="https://js.stripe.com/v2/"></script>
<script type="text/javascript">
$(document).ready(function () {
    $("#bank-account-form").submit(function (event) {
        $('.submit-button').attr("disabled", "disabled");
        var bankAccountParams = {
            country: $('.country').val(),
            currency: $('.currency').val(),
            account_number: $('.account-number').val(),
        		account_holder_name: $('.account-holder-name').val(),
            account_holder_type: $('.account-holder-type').val()
        }
        if ($('.routing-number').val() != '') {
        	bankAccountParams['routing_number'] = $('.routing-number').val();
        }
        Stripe.bankAccount.createToken(bankAccountParams, stripeResponseHandler);
        return false; // submit from callback
    });
});
 </script>
 <script type="text/javascript">
            Stripe.setPublishableKey('pk_test_nSKUtjjdNWvfiQR6hX2FuLlj');
 			 function stripeResponseHandler(status, response) {
 				 
                if (response.error) {
                	bootbox.alert({ 
                		  size: "small",
                		  title: "Account Information",
                		  message: "<span style='color: red;font-weight: bold;'>Invalid account number.<span>"
                	});
                	
                	//window.location.reload(true);
                } else {
                   var token = response['id'];
                   var Country = document.getElementById("Country").value ;
                   var Currency = document.getElementById("Currency").value;
                   var routingNumber = document.getElementById("routingNumber").value ;
                   var accountNumber = document.getElementById("accountNumber").value ;
                   var accountHolderName = document.getElementById("accountHolderName").value;
                   var accountHolderType = document.getElementById("accountHolderType").value ;
                   var agree = document.getElementById("agree1").value ;
                   document.getElementById("modalwait").style.display  = "";
                   document.getElementById("formID").style.display  = "none";
                     $.ajax({
                        type: 'POST',
                        url: "addBankDetail?bank="+token+"&Country="+Country+"&Currency="+Currency+"&routingNumber="+routingNumber+"&accountNumber="+accountNumber+"&accountHolderName="+accountHolderName+"&accountHolderType="+accountHolderType+"&agree="+agree+"",
                        success:function(data){
                        	  window.location.reload(true);
                        }
                    
                    });
                }
            }
           
</script>
<div class="container-wrapper">
    <div class="container">
 <form id="bank-account-form"  method="POST" class="form-horizontal" action="addBankDetail"  >
 <div id="modalwait" style="display: none">
        <div align="center" style="margin-right:300px">
          <img width="40%;height:10%" class="img-responsive" src="images/loader.gif"/>
        </div>
    </div>
   <div id="formID" style="display: ">     
 <div class="form-group"></div>
 
    <div class="form-group">
       <div class="col-md-1"></div>
        <div class="col-md-3" > <label for="country">Country</label></div>
        <div class="col-md-4">
        <select class="country form-control" name="Country" id="Country"  required="required">
            <option value="US" selected="selected">United States</option>
            <option value="CA">Canada</option>
            <option value="GB">United Kingdom</option>
            <option value="DE">Germany</option>
            <option value="FR">France</option>
            <option value="ES">Spain</option>
        </select>
        </div>
          <div class="col-md-2"></div>
    </div>
    <div class="form-group">
     <div class="col-md-1"></div>
        <div class="col-md-3">
        <label for="currency">Currency</label></div>
        <div class="col-md-4">
        <select class="currency form-control" name="Currency" id="Currency"  required="required">
            <option value="USD" selected="selected" >US Dollar</option>
            <option value="CAD">Canadian Dollar</option>
            <option value="GBP">Pound</option>
            <option value="EUR" >Euro</option>
        </select>
        </div>
          <div class="col-md-2"></div>
    </div>
    <div class="form-group">
    <div class="col-md-1"></div>
        <div class="col-md-3"><label for="routing-number">Routing Number</label></div>
        <div class="col-md-4">
        <input required="required" type="text" name="routingNumber" maxlength="12" id="routingNumber" class="routing-number form-control" value="" placeholder="110000000" />
        </div>
        <div class="col-md-2"></div>
    </div>
    <div class="form-group">
    <div class="col-md-1"></div>
        <div class="col-md-3">
        <label for="account-number form-control">Account Number</label></div>
         <div class="col-md-4">
        <input type="text" required="required" maxlength="12" name="accountNumber" id="accountNumber"  class="account-number form-control" value="" placeholder="000123456789"/>
         </div>
        <div class="col-md-2"></div>
    </div>
    <div class="form-group">
     <div class="col-md-1"></div>
        <div class="col-md-3">
        <label for="account-holder-name">Account Holder Name</label></div>
         <div class="col-md-4">
        <input type="text" required="required" name="accountHolderName" id="accountHolderName" class="account-holder-name form-control" value="Anand Alok" />
          </div>
        <div class="col-md-2"></div>
    </div>
    <div class="form-group">
    <div class="col-md-1"></div>
        <div class="col-md-3">
        <label for="account-holder-type">Account Holder Type</label></div>
        <div class="col-md-4">
         <select required="required" name="accountHolderType" id="accountHolderType" class="account-holder-type form-control">
            <option value="individual">Individual</option>
            <option value="company">Company</option>
        </select>
         </div>
        <div class="col-md-2"></div>
    </div>
     <div class="form-group" id="bankTerms" style="display: none">
        
        <div class="col-md-2"></div>
        <label class="col-xs-2 control-label">Terms of use</label>
        <div class="col-xs-4">
            <div style="border: 1px solid #e5e5e5; height: 200px; overflow: auto; padding: 10px;">
                <p>Lorem ipsum dolor sit amet, veniam numquam has te. No suas nonumes recusabo mea, est ut graeci definitiones. His ne melius vituperata scriptorem, cum paulo copiosae conclusionemque at. Facer inermis ius in, ad brute nominati referrentur vis. Dicat erant sit ex. Phaedrum imperdiet scribentur vix no, ad latine similique forensibus vel.</p>
                <p>Dolore populo vivendum vis eu, mei quaestio liberavisse ex. Electram necessitatibus ut vel, quo at probatus oportere, molestie conclusionemque pri cu. Brute augue tincidunt vim id, ne munere fierent rationibus mei. Ut pro volutpat praesent qualisque, an iisque scripta intellegebat eam.</p>
                <p>Mea ea nonumy labores lobortis, duo quaestio antiopam inimicus et. Ea natum solet iisque quo, prodesset mnesarchum ne vim. Sonet detraxit temporibus no has. Omnium blandit in vim, mea at omnium oblique.</p>
              </div>
        </div>
         <div class="col-md-2"></div>
    </div>
      <div class="form-group">
    <div class="col-md-4"></div>
    <div class="col-md-4">
     <p style="border-style: solid; border-color: #C0C0C0; border-radius: 30px ; padding: 10px;">
    <input type="checkbox" name="agree"  id="agree1"  required="required" value="agree"  onclick="bankTerms('none');" /> 
    Agree with the <a onclick="return bankTerms('');" ><span  style="color: red">terms and conditions</span></a>
     </p>
           
        </div>
        <div class="col-md-4"></div>
    </div>
         <div class="form-group">
          <div class="col-md-6"> </div>
              	 <span class="payment-errors"></span>
				<span class="payment-success"></span>
 				<div class="col-md-2"> 
           		 <button name="savebankBtn" id="savebankBtn" class="submit-button btn btn-lg btn-primary btn-block" type="submit" >Save</button>
         		 </div>
         		 <div class="col-md-4"> </div>
         		 <div class="clearfix"></div>
         		 </div>
</div>
</form>
</div>
</div>
