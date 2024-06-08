
 function addNewBrand(button) {
     // Find the closest form container from the button
     var formContainer = button.closest('#innerFormContent');

     // Get the category ID, brand name, description, and the brand logo file
     var cid = formContainer.querySelector('#cid').value;
     var brandName = formContainer.querySelector('#brandName').value;
     var description = formContainer.querySelector('#description').value;
     var brandLogo = formContainer.querySelector('#brandLogo').files[0];

     // Create a FormData object to hold the form data
     const formData = new FormData();
     formData.append('brand_name', brandName);
     formData.append('descriptions', description); // Ensure the key matches the server's expected parameter
     formData.append('brand_Logo', brandLogo);

     // Construct the URL with the category ID
     const url = `/category/${cid}/brand`;

     // Send the POST request using fetch
     fetch(url, {
         method: 'POST',
         body: formData
     })
     .then(response => {
         if (response.ok) {
              return response.text();
         } else {
              throw new Error("Failed to add address");
         }
     })
    .then(data => {
          $(".custom-model-main").removeClass('model-open');
                 document.getElementById('brow').insertAdjacentHTML('beforeend', `<option value="${data}">${brandName}</option>`);

     })
     .catch(error => {
         console.error('Error:', error);
         // Handle the error response (e.g., show an error message)
     });
 }

function deleteWishList(button) {
         var cardContainer = button.closest('.col');
         var wishlist_value = cardContainer.querySelector('.wishlist-value');
         var wId = parseInt(wishlist_value.textContent);
         fetch("/remove/wishlist/" + wId, {
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