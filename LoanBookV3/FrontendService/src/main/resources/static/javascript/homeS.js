$(document).ready(function() {
    // const backendUrl = 'http://localhost/api/auth';
    const backendUrl1 = 'http://localhost/api/books';
    const LocalUrl = '/api/frontend';
    const cartKey = "userCart";
    let check = false;







    //List the books for reservations

    $.ajax({
        type: "GET",
        url: backendUrl1+"/ListBook",
        success: function(response) {
            const books = response;
            const tbody = $("#book-list tbody");
            tbody.empty(); // Clear any existing rows


            books.forEach(book => {
                const row = `<tr>
                            <td>${book.author}</td>
                            <td>${book.nb_of_books}</td>
                            <td>${book.title}</td>
                            <td>${book.reservationsCount}</td>

                            <td>
                                <button class="add-to-cart-btn fas fa-shopping-cart" data-id="${book.id}"
                                data-author="${book.author}"
                                data-title="${book.title}"
                                data-nb_of_books="${book.nb_of_books}"></button>
                            </td>
                        </tr>`;
                tbody.append(row);
            });

            // Show the book list section
            $("#book-list-section").show();
        },
        error: function(error) {
            alert("Error fetching books: " + error.responseText);
        }
    });






    // Load cart from localStorage
    let cart = JSON.parse(localStorage.getItem(cartKey)) || [];

    // Update the mini cart with items from localStorage
    cart.forEach(book => {
        const cartItem = `<li>${book.title} by ${book.author}</li>`;
        $("#cart-items").append(cartItem);
        $("#cart-items")[0].offsetHeight; // Forces reflow
    });
    // Add to cart functionality
    $(document).on('click', '.add-to-cart-btn', function() {
        const bookId = $(this).data('id');
        const author = $(this).data('author');
        const title = $(this).data('title');
        const number = $(this).data('nb_of_books')
        if (bookId && author && title && number > 0) {
            const book = { id: bookId, author: author, title: title };

            if (!cart.some(item => item.id === bookId)) {
                cart.push(book);

                // Save cart to localStorage
                localStorage.setItem(cartKey, JSON.stringify(cart));

                // Show flash message
                $("#flash-message").text("Added to cart successfully!").fadeIn().delay(2000).fadeOut();

                // Update the mini cart
                if (cart.length > 0) {
                    updateMiniCart();
                }
            }
        } else {
            console.error('Invalid book data:', { bookId, author, title });
            if(number<=0){
                //add flash message red due to lack of numbers of books
            }
        }
    });
    // Function to update mini cart
    function updateMiniCart() {
        $("#cart-items").empty(); // Clear the table body

        if (cart.length > 0) {
            $("#cart-table-header").show(); // Show table headers
            $("#reserve-btn").show(); // Show the reserve button

            // Loop through each item in the cart and add it as a table row
            cart.forEach(book => {
                if (book.title && book.author) {
                    const cartItemRow = `<tr>
                                        <td>${book.author}</td>
                                        <td>${book.title}</td>
                                     </tr>`;
                    $("#cart-items").append(cartItemRow);
                } else {
                    console.error('Invalid book in cart:', book);
                }
            });

            // Ensure the mini cart is visible
            $("#mini-cart").show();

        } else {
            $("#cart-table-header").hide(); // Hide table headers
            $("#reserve-btn").hide(); // Hide the reserve button
        }
    }
    // Attach the reserve button click event only once
    $("#reserve-btn").click(function() {
        if (cart.length > 0) {
            // Handle reservation logic here, e.g., send the cart data to the server
            $.ajax({
                url: '/api/reservations/reserve',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    userId: localStorage.getItem('id'),
                    bookIds: cart.map(book => book.id)
                }),
                success: function(response) {
                    alert('Reservation successful');
                },
                error: function(error) {
                    console.error('Reservation failed:', error);
                }
            });
            alert("Reservation process initiated.");

            // Clear the cart
            cart = [];
            localStorage.removeItem(cartKey);

            // Clear the mini cart display
            $("#cart-items").empty();
            $("#mini-cart").hide();
        }
    });
    // Toggle mini cart display
    $("#cart-container a").click(function(event) {
        event.preventDefault();
        $("#mini-cart").toggle();
    });






    //logout

    $("#logout").click(function(event) {
        event.preventDefault(); // Prevent default link behavior

        if(check===false){
            localStorage.removeItem('id');
            localStorage.removeItem('userName');
            localStorage.removeItem('role');
            check = true;
            $.ajax({
                url: LocalUrl+'/logout',
                type: 'POST',
                success: function(response) {
                    window.location.href = LocalUrl+'/login';
                    console.log(response);
                },
                error: function(error) {
                    console.error('Error logging out:', error);
                }
            });
        }
        else {
            window.location.href = LocalUrl+"/loginError";
        }
        console.log(check)
    });




    //List the books and the reservation process
    $("#view-books").click(function(event) {
        event.preventDefault(); // Prevent default link behavior

    });

});