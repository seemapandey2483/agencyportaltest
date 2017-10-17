<script src="https://js.stripe.com/v3/"></script>
<script type="text/javascript">
  var stripe;
  var card;
  $(document).ready(function () {
    stripe = Stripe('pk_test_nSKUtjjdNWvfiQR6hX2FuLlj');
	var elements = stripe.elements();
     card = elements.create('card', {
	  style: {
	    base: {
	      iconColor: '#666EE8',
	      color: '#31325F',
	      lineHeight: '40px',
	      fontWeight: 300,
	      fontFamily: 'Helvetica Neue',
	      fontSize: '15px',
	      '::placeholder': {
	        color: '#CFD7E0',
	      },
	    },
	  }
	});
	card.mount('#card-element');
    
    card.on('change', function(event) {
      if (event.error) {
        setOutcome(event);
      }
    });
  });
  
  function saveCardInfo(result) {
  			var options = {
		   	 	name: document.getElementById('name').value,
		 	 };
			stripe.createToken(card,options).then(setOutcome);
  }
  
  function setOutcome(result) {
	if (!result.error) {
    	 document.getElementById("cardForm").style.display  = "none";
     	 document.getElementById("modalLoder").style.display  = "";
     		$.ajax({
          			type: 'POST',
          			url: "addBankDetail?card="+result.token.id,
          			success:function(data){
        	 		 window.top.location.reload();
          			}
     			 });
    		}
  }
  
  function cardTerms (obj) {
		document.getElementById("cardTerms").style.display  = obj;
	}
</script>


 <form id="payment-form"  method="POST" class="form-horizontal" action="addBankDetail"  >
 <div id="modalLoder" style="display: none" >
       <div align="center" >
          <img width="30%;height:10%" class="img-responsive" src="images/loader.gif"/>
        </div>
    </div>
   <div id="cardForm" style="display: ">
    <div class="form-group">
    <div class="col-md-1"></div>
        <div class="col-md-3"><label for="Name">Name</label></div>
        <div class="col-md-4">
        <input required="required" class="form-control" type="text" id="name" name="name" placeholder="Jane Doe" />
        </div>
        <div class="col-md-2"></div>
    </div>
     <div class="form-group">
    <div class="col-md-1"></div>
        <div class="col-md-3"><label for="card">Card</label></div>
        <div class="col-md-4">
        <div id="card-element" ></div>
        </div>
        <div class="col-md-2"></div>
    </div>
   
   <div class="form-group" id="cardTerms" style="display: none">
             <div class="col-md-2"></div>
        <label class="col-xs-2 control-label">Terms of use</label>
        <div class="col-xs-6">
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
       <input type="checkbox" name="agree"  id="agree"  required="required" value="agree"  onclick="cardTerms('none');" /> Agree with the <a onclick="cardTerms('');"><span  style="color: red">terms and conditions</span></a>
      </p>
    </div>
   <div class="col-md-4"></div>
    </div>
		<div class="form-group">
               <div class="col-md-8"> </div>
 				<div class="col-md-2"> 
           		 <button name="addbankBtn" id="addbankBt" class="btn btn-lg btn-primary btn-block" onclick="saveCardInfo();" type="button" >Save</button>
         		 </div>
         		 <div class="col-md-2"> </div>
         		 </div>
         		 
   </div>
 </form>
