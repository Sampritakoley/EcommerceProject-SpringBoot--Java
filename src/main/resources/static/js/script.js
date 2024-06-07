const toggleSidebar = () =>{
  if($(".sidebar").is(":visible")){
     $(".sidebar").css("display","none")
     $(".content").css("margin-left","0%")
  }
  else{
    $(".sidebar").css("display","block")
    $(".content").css("margin-left","18%")
  }
};

const deleteContact=(cid)=>{
   Swal.fire({
     title: "Are you sure?",
     text: "you want to delete this content?",
     icon: "warning",
     showCancelButton: true,
     confirmButtonColor: "#3085d6",
     cancelButtonColor: "#d33",
     confirmButtonText: "Yes, delete it!"
   }).then((result) => {
     if (result.isConfirmed) {
       window.location.href = "/remove-contacts/"+cid;
     }
   });

};



$(".Click-here").on('click', function() {
  $(".custom-model-main").addClass('model-open');
});
$(".close-btn, .bg-overlay").click(function(){
  $(".custom-model-main").removeClass('model-open');
});


$(".Click-here-delivery").on('click', function() {
  $(".custom-model-main-delivery").addClass('model-open');
});
$(".close-btn-delivery, .bg-overlay").click(function(){
  $(".custom-model-main-delivery").removeClass('model-open');
});



function addToCart(button) {
        var cardContainer = button.closest('.card-body');
        var itemElement = cardContainer.querySelector('.itemId-value');
        var itemId = parseInt(itemElement.textContent);

        button.innerText = 'Go to cart';

        fetch("/cart/items/" + itemId, {
            method: "GET",
        })
        .then(response => {
            if (!response.ok) {
                console.error("Error:", response.statusText);
            }
            refreshPage();
        })
        .catch(error => {
            console.error("Error:", error);
        });
    }

function addWishList(button) {
        var cardContainer = button.closest('.card-body');
        var itemElement = cardContainer.querySelector('.itemId-value');
        var itemId = parseInt(itemElement.textContent);

        button.style.backgroundColor = 'white';
        button.style.color = '#f44336';



        fetch("/add/wishlist/item/" + itemId, {
            method: "GET",
        })
        .then(response => {
            if (!response.ok) {
                console.error("Error:", response.statusText);
            }
            refreshPage();
        })
        .catch(error => {
            console.error("Error:", error);
        });
    }

    function removeWishList(button) {
        var cardContainer = button.closest('.card-body');
        var itemElement = cardContainer.querySelector('.itemId-value');
        var itemId = parseInt(itemElement.textContent);

        var wishlistElement = cardContainer.querySelector('.wishlist-value');
        var wishlistId = parseInt(wishlistElement.textContent);

        button.style.backgroundColor = '#f44336';
        button.style.color = 'white';




        fetch("/remove/wishlist/" + wishlistId, {
            method: "GET",
        })
        .then(response => {
            if (!response.ok) {
                console.error("Error:", response.statusText);
            }
            refreshPage();
        })
        .catch(error => {
            console.error("Error:", error);
        });
    }
function refreshPage() {
    location.reload();
}

function incrementQuantity(button) {
    var qtyContainer = button.closest('.qty-container');
    if (qtyContainer) {
        var quantityElement = qtyContainer.querySelector('.quantity-value');
        var currentQuantity = parseInt(quantityElement.textContent);
        quantityElement.textContent = currentQuantity + 1;

        var priceElement = qtyContainer.closest('.row').querySelector('.price-value');

        if (priceElement) {
            var currentPrice = parseFloat(priceElement.textContent);
            console.log(currentPrice);

            var eachPriceElement =qtyContainer.closest('.row').querySelector('.eachItemPrice');
            var cartItemId=qtyContainer.closest('.row').querySelector('.cartItemId');
            if (eachPriceElement) {
                var eachPrice = parseFloat(eachPriceElement.textContent);
                priceElement.textContent = (currentPrice + eachPrice).toFixed(2);
            }
            if(cartItemId){
                 var cid = parseInt(cartItemId.textContent);
                 fetch("/add/cart-item/"+cid+"/item", {
                                                     method: "POST",
                                                 })
                                                 .then(response => {
                                                     // Check if the response is successful (HTTP status code 200-299)
                                                     if (response.ok) {
                                                       window.location.href = "/cart/item/"
                                                         // Redirect to the previous URL or perform other actions
                                                         // Replace [previousUrl] with the actual value
                                                     } else {
                                                         // Handle the error response
                                                         console.error("Error:", response.statusText);
                                                     }
                                                 })
                                                 .catch(error => {
                                                     // Handle network errors or other exceptions
                                                     console.error("Error:", error);
                                                 });
            }
        }
    }

}


function decrementQuantity(button) {
    var quantityElement = button.parentNode.querySelector('.quantity-value');
    var currentQuantity = parseInt(quantityElement.textContent);
    if (currentQuantity > 0) {
       var qtyContainer = button.closest('.qty-container');
           if (qtyContainer) {

               quantityElement.textContent = currentQuantity - 1;

               var priceElement = qtyContainer.closest('.row').querySelector('.price-value');
               if (priceElement) {
                   var currentPrice = parseFloat(priceElement.textContent);
                   console.log(currentPrice);

                   var eachPriceElement =qtyContainer.closest('.row').querySelector('.eachItemPrice');
                    var cartItemId=qtyContainer.closest('.row').querySelector('.cartItemId');
                   if (eachPriceElement) {
                       var eachPrice = parseFloat(eachPriceElement.textContent);


                        priceElement.textContent = (currentPrice - eachPrice).toFixed(2);
                        if (currentQuantity-1 === 0) {
                                               // Hide the parent row element instantly
                                               var rowElement = qtyContainer.closest('.row');
                                               var colElement=rowElement.closest('.col');
                                               var parentRow=colElement.closest('.row');
                                               var cardElement=parentRow.closest('.card');
                                               cardElement.style.display = 'none';
                                           }

                   }
                   if(cartItemId){
                                    var cid = parseInt(cartItemId.textContent);
                                    fetch("/remove/cart-item/"+cid+"/item", {
                                                                        method: "POST",
                                                                    })
                                                                    .then(response => {
                                                                        // Check if the response is successful (HTTP status code 200-299)
                                                                        if (response.ok) {
                                                                        window.location.href = "/cart/item/"
                                                                            // Redirect to the previous URL or perform other actions
                                                                            // Replace [previousUrl] with the actual value
                                                                        } else {
                                                                            // Handle the error response
                                                                            console.error("Error:", response.statusText);
                                                                        }
                                                                    })
                                                                    .catch(error => {
                                                                        // Handle network errors or other exceptions
                                                                        console.error("Error:", error);
                                                                    });
                               }
               }
           }
    }
}

//add warehouse
  function addWarehouse(button) {
        var formContainer = button.closest('#innerFormContent');
        var state = formContainer.querySelector('#state').value;
        var city = formContainer.querySelector('#City').value;
        var street = formContainer.querySelector('#Street').value;
        var type= formContainer.querySelector('#Type').value;
        var addressLine = formContainer.querySelector('#address-line').value;
        var zip = formContainer.querySelector('#zip').value;

        var userIdElement=formContainer.querySelector('.userId-value');

        var userId = parseInt(userIdElement.textContent);
        console.log(userId)

        var addressData = {
                state: state,
                city: city,
                street: street,
                type:type,
                address_line: addressLine,
                zip: zip
            };
        fetch('/warehouse', {
                method: 'POST', // Define the method
                headers: {
                    'Content-Type': 'application/json' // Specify JSON content type
                },
                body: JSON.stringify(addressData) // Convert the object to JSON
            })
            .then(response => {
                if (response.ok) {
                    $(".custom-model-main").removeClass('model-open');
                    window.location.href = "/warehouse"
                } else {
                    throw new Error("Failed to add address");
                }
            })

    }

//add delivery date
function addDeliveryDate(button){
 var formContainer = button.closest('#innerFormContent1');
  var orderIdElement=formContainer.querySelector('.order-id');
   var deliveryDate=formContainer.querySelector('#deliveryDate').value;
       var orderId = parseInt(orderIdElement.textContent);

       var orderTIdElement=formContainer.querySelector('.order-tracking-id').textContent;


       var updateOrderModel = {
                       deliveryDate: deliveryDate,
                       warehouseId: null,
                       status: null
                   };
               fetch('/process-order/'+orderId, {
                       method: 'POST', // Define the method
                       headers: {
                           'Content-Type': 'application/json' // Specify JSON content type
                       },
                       body: JSON.stringify(updateOrderModel) // Convert the object to JSON
                   })
                   .then(response => {
                       if (response.ok) {

                           return response.json();
                       } else {
                           throw new Error("Failed to add address");
                       }
                   }).then(val => {


                                              let jsonOb={
                                                     orderId:val.orderId,
                                                     to:val.to,
                                                     type:val.type
                                              }
                                              stompClient.send("/app/private", {},
                                                                JSON.stringify(jsonOb));
                                              $(".custom-model-main-delivery").removeClass('model-open');
                                              window.location.href = "/order-manage/"+orderTIdElement;
                                        })
                                        .catch(error => {
                                              console.log("error")
                                              console.error("Error:", error);
                                   });

}


    //processOrder
 function ProcessOrder(button){
      var formContainer = button.closest('#innerFormContent');
      var warehouseId=formContainer.querySelector('#warehouse').value;
      var statusOption=formContainer.querySelector('#status').value;


      var orderIdElement=formContainer.querySelector('.order-id');
      var orderId = parseInt(orderIdElement.textContent);

      var orderTIdElement=formContainer.querySelector('.order-tracking-id').textContent;


      var updateOrderModel = {
                      deliveryDate: null,
                      warehouseId: warehouseId,
                      status: statusOption
                  };
              fetch('/process-order/'+orderId, {
                      method: 'POST', // Define the method
                      headers: {
                          'Content-Type': 'application/json' // Specify JSON content type
                      },
                      body: JSON.stringify(updateOrderModel) // Convert the object to JSON
                  })
                  .then(response => {
                      if (response.ok) {

                          $(".custom-model-main").removeClass('model-open');
                          window.location.href = "/order-manage/"+orderTIdElement;
                          return response.json();
                      } else {
                          throw new Error("Failed to add address");
                      }
                  }).then(val => {


                                                                   let jsonOb={
                                                                          orderId:val.orderId,
                                                                          to:val.to,
                                                                          type:val.type
                                                                   }
                                                                   stompClient.send("/app/private", {},
                                                                                     JSON.stringify(jsonOb));
                                                                   $(".custom-model-main-delivery").removeClass('model-open');
                                                                   window.location.href = "/order-manage/"+orderTIdElement;
                                                             })
                                                             .catch(error => {
                                                                   console.log("error")
                                                                   console.error("Error:", error);
                                                        });
 }

//add address
  function addAddress(button) {
        var formContainer = button.closest('#innerFormContent');
        var state = formContainer.querySelector('#state').value;
        var city = formContainer.querySelector('#City').value;
        var street = formContainer.querySelector('#Street').value;
        var addressLine = formContainer.querySelector('#address-line').value;
        var zip = formContainer.querySelector('#zip').value;

        var userIdElement=formContainer.querySelector('.userId-value');

        var userId = parseInt(userIdElement.textContent);
        console.log(userId)

        var addressData = {
                state: state,
                city: city,
                street: street,
                address_line: addressLine,
                zip: zip
            };
        fetch('/user/'+userId+'/address', {
                method: 'POST', // Define the method
                headers: {
                    'Content-Type': 'application/json' // Specify JSON content type
                },
                body: JSON.stringify(addressData) // Convert the object to JSON
            })
            .then(response => {
                if (response.ok) {

                } else {
                    throw new Error("Failed to add address");
                }
            })
            $(".custom-model-main").removeClass('model-open');
            window.location.href = "/address"
    }



//connect websocket

var stompClient=null;
var privateStompClient = null;

//connect private channel
/*function connectPrivateChannel(){

   let socket = new SockJS('/server1');
   privateStompClient = Stomp.over(socket);
   privateStompClient.connect({}, function(frame) {
   console.log(frame);
   privateStompClient.subscribe('/user/specific', function(response) {

        const mesg = JSON.parse(response.body);
        console.log(mesg)
        show(mesg);
   });
  });
}*/


function show(mesg){
     console.log(mesg.type+ "  ")
}

//connect admin
function connect(role)
{
      var socket=null;
      if(role=== "ROLE_ADMIN"){
         socket=new SockJS('/server1')
               stompClient=Stomp.over(socket)
               stompClient.connect({},function(frame){
                  console.log(frame)
                  let userName = frame.headers["user-name"];
                  console.log("Username is:"+userName)
                                 //subscribe
                  stompClient.subscribe('/topic/return-to',function(response){

                                         const message = JSON.parse(response.body);
                                         console.log(message)
                                         showMessage(message,userName)

                   })
               })
      }


       socket = new SockJS('/server1');
       privateStompClient = Stomp.over(socket);
       privateStompClient.connect({}, function(frame) {
       console.log(frame);
       privateStompClient.subscribe('/user/specific', function(response) {

            const mesg = JSON.parse(response.body);
            console.log(mesg)
            showMessage(mesg,mesg.to);
       });
   });
}

 function showMessage(message,userName)
 {

     console.log(message.userId+"   "+message.orderId+" "+message.type+" "+userName)

    const data = {
        type: message.type, // Example type
        orderId: message.orderId,
        userName:userName
    };

    // Send the POST request using fetch
    fetch("/notification", {
        method: "POST",
        headers: {
            "Content-Type": "application/json" // Make sure to set the correct Content-Type
        },
        body: JSON.stringify(data) // Convert data to JSON
    })
    .then(response => {
                                if (response.ok) {
                                    return response.json(); // Parse JSON from the response
                                } else {
                                    console.log("error")
                                    throw new Error("Failed to send Notification");
                                }
                            })
                            .then(val => {
                                console.log("data is", val);
                                var notificationType=val.type;
                                var notificationMsg=val.message;
                                var typeContainer = document.getElementById('notification-type-id');
                                var messageContainer = document.getElementById('notification-contentId');
                                typeContainer.innerHTML =notificationType;
                                messageContainer.innerHTML=notificationMsg;
                                let popup = document.getElementById("popup1");
                                popup.style.display = "flex";
                                console.log(notificationMsg+"  "+notificationType)
                            })
                            .catch(error => {
                               console.log("error")
                                console.error("Error:", error);
                            });

    /*.then(response => {
        if (response.ok) {
            console.log("Notification sent successfully!");
        } else {
            console.error("Failed to send notification");
        }
    })
    .catch(error => {
        console.error("Error:", error);
    });*/




   // $("#message-container-table").prepend(`<tr><td><b>${message.name} :</b> ${message.content}</td></tr>`)

 }

//-> subscribe the admin after login
document.addEventListener("DOMContentLoaded", function() {
    console.log("checkUserRole called listener");
    const form = document.querySelector("form");
    if (form) {
    console.log("fetching user role")
         fetch("/user/role")
                 .then(response => response.json())
                 .then(data => {
                     console.log(data.role)
                     connect(data.role);
                     /*if (data.role === "ROLE_ADMIN") {
                         console.log(data.role)
                         connect(data.role);
                     }/*if (data.role === "ROLE_USER" || data.role === "ROLE_ADMIN") {
                         console.log(data.role)
                         connectPrivateChannel();
                     }*/
                 })
                 .catch(error => console.error("Error fetching user role:", error));
    }
});

function closePopUp(button){
  var popupContainer = button.closest('.popup');
  var popUp=popupContainer.closest("#popup1");
  popUp.style.display = 'none';
}

var orderedCartId=null;
    //after placing order
    function orderPlace(button) {
            var orderContainer = button.closest('.order-container');
            var cartId = orderContainer.querySelector('.cartId');
            var cart = parseInt(cartId.textContent);
            sessionStorage.setItem('orderedCartId', cart);
            window.location.href = "/order-confirmation"
 }


//after selecting billing address
var selectedAddressId = null;
      function selectAddress(card) {
        console.log(sessionStorage.getItem('orderedCartId'));
                 // Remove 'card-selected' from all cards
                 var cards = document.querySelectorAll('.card-address');
                 for (var i = 0; i < cards.length; i++) {
                     cards[i].classList.remove('card-selected');
                 }

                 // Add 'card-selected' to the clicked card
                 card.classList.add('card-selected');

                 // Update "show-panel" with the selected address
                 var showPanel = document.getElementById('show-panel');
                 var State=card.querySelector('.card-title').textContent;
                 var City=card.querySelector('#cityId').textContent;
                 var Street=card.querySelector('#streetId').textContent;
                 var Address=card.querySelector('#address-line-id').textContent;
                 var addressId = card.querySelector('.address-id-value').textContent;
                 sessionStorage.setItem('selectedAddressId', addressId);
                 showPanel.innerHTML = 'Selected address: ' + Address +", "+Street+", "+City+", "+State ;
                 console.log(showPanel)
             }

function order(button){
     var cartId=sessionStorage.getItem('orderedCartId');
     console.log(cartId)
     AddressId=parseInt(sessionStorage.getItem('selectedAddressId'));
     var requestBody = JSON.stringify(AddressId);
     console.log(sessionStorage.getItem('selectedAddressId'));
     fetch("/order/" + cartId, {
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        body: requestBody,
                    })
                    .then(response => {
                        if (response.ok) {
                            return response.json(); // Parse JSON from the response
                        } else {
                            console.log("error")
                            throw new Error("Failed to place order");
                        }
                    })
                    .then(data => {
                        console.log("data is", data);
                        let jsonOb={
                                userId:data.userId,
                                orderId:data.orderId,
                                type:data.type,
                            }
                            stompClient.send("/app/message",{},JSON.stringify(jsonOb));


                        // You can do something with the order ID here
                    })
                    .catch(error => {
                       console.log("error")
                        console.error("Error:", error);
                    });

                    fetch("/orders")
                               .then(response => {
                                                     if (response.ok) {
                                                          // Parse JSON from the response
                                                     } else {
                                                     console.log("error")
                                                         throw new Error("Failed to place order");
                                                     }
                                                 })
                                     .catch(error => console.error("Error fetching user role:", error));

                                     sessionStorage.setItem('orderedCartId', null);
                                     sessionStorage.setItem('selectedAddressId', null);
                                     window.location.href = "/orders"
}
function searchKey(button)
{
  var searchContainer = button.closest('.search-bar');
  var searchElement = searchContainer.querySelector('#search-key').value;
  window.location.href = "/search/"+searchElement+"/0";
}

