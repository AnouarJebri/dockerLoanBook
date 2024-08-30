//logout
$(document).ready(function() {
    const backendUrl = 'http://localhost/api/auth';
    const backendUrl1 = 'http://localhost/api/books';
    const backendUrl2 ='http://localhost/api/reservations';
    const LocalUrl = '/api/frontend';
    let check = false;
    $("#logout").click(function(event) {
        event.preventDefault(); // Prevent default link behavior

        if(check===false){
            localStorage.clear();
            check = true;
            $.ajax({
                url: LocalUrl+'/logout',
                type: 'POST',
                success: function(response) {
                    window.location.href = LocalUrl+'/login';
                },
                error: function(error) {
                    console.error('Error logging out:', error);
                }
            });
        }
        else {
            window.location.href = LocalUrl+'/loginError';
        }
        console.log(check)
    });

    // List the users
    $("#view-users").click(function(event) {
        event.preventDefault(); // Prevent default link behavior

        $.ajax({
            type: "GET",
            url: backendUrl + "/users",
            success: function(response) {
                const users = response;
                const tbody = $("#user-list tbody");
                tbody.empty(); // Clear any existing rows

                users.forEach(user => {
                    // Construct a string to display all book titles and authors
                    let books = '';
                    if (user.reservation && user.reservation.length > 0) {
                        books = user.reservation.join(', ');
                    } else {
                        books = 'No books reserved';
                    }

                    const row = `<tr>
                    <td>${user.nom}</td>
                    <td>${user.prenom}</td>
                    <td>${user.email}</td>
                    <td>${user.role}</td>
                    <td>${books}</td>
                </tr>`;
                    tbody.append(row);
                });

                // Show the user list section
                $("#user-list-section").show();
                $("#book-list-section").hide();
            },
            error: function(error) {
                alert("Error fetching users: " + error.responseText);
            }
        });

        $('#returnBooksBtn').on('click', function() {
            $.ajax({
                url: backendUrl2+'/return',
                type: 'POST',
                contentType: 'application/json',
                success: function(response) {
                    showFlashMessage('Success: ' + response, 'success');
                },
                error: function(xhr, status, error) {
                    showFlashMessage('Error: ' + xhr.responseText, 'error');
                }
            });
        });

        function showFlashMessage(message, type) {
            var $flashMessage = $('#flashMessage');
            $flashMessage
                .text(message)
                .removeClass('success error')
                .addClass(type)
                .fadeIn(400)
                .delay(3000)
                .fadeOut(400);
        }
    });





    //List the books
    $("#view-books").click(function(event) {
        event.preventDefault(); // Prevent default link behavior

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
                                <button class="edit-book-btn fas fa-edit" data-id="${book.id}"
                                    data-author="${book.author}"
                                    data-nb_of_books="${book.nb_of_books}"
                                    data-title="${book.title}"></button>
                                <button class="delete-book-btn fas fa-trash-alt" data-id="${book.id}"></button>
                            </td>
                        </tr>`;
                    tbody.append(row);
                });

                // Show the book list section
                $("#book-list-section").show();
                $("#user-list-section").hide();
            },
            error: function(error) {
                alert("Error fetching books: " + error.responseText);
            }
        });


    });






    // Show the form when the add book button is clicked
    $("#add-book-btn").click(function() {
        $("#add-book-form").toggle();
        $("#edit-book-form").hide();
    });

    // Handle form submission for adding a new book
    $("#new-book-form").submit(function(event) {
        event.preventDefault();

        const newBook = {
            author: $("#author").val(),
            nb_of_books: $("#nb_of_books").val(),
            title: $("#title").val()
        };

        $.ajax({
            type: "POST",
            url: backendUrl1+"/BookAdd",
            contentType: "application/json",
            data: JSON.stringify(newBook),
            success: function(response) {
                alert(response); // Show success message

                // Append the new book to the table
                const tbody = $("#book-list tbody");
                const row = `<tr>
                    <td>${newBook.author}</td>
                    <td>${newBook.nb_of_books}</td>
                    <td>${newBook.title}</td>

                     <td>
                        <button class="edit-book-btn fas fa-edit" data-id="${newBook.id}"
                            data-author="${newBook.author}"
                            data-nb_of_books="${newBook.nb_of_books}"
                            data-title="${newBook.title}"></button>
                        <button class="delete-book-btn fas fa-trash-alt" data-id="${newBook.id}"></button>
                    </td>
                </tr>`;
                tbody.append(row);

                // Hide the form and reset it
                $("#add-book-form").hide();
                $("#new-book-form")[0].reset();
            },
            error: function(error) {
                alert("Error adding book: " + error.responseText);
            }
        });
    });




    // Handle edit book button click
    $(document).on("click", ".edit-book-btn", function() {
        const bookId = $(this).data("id");
        const author = $(this).data("author");
        const nb_of_books = $(this).data("nb_of_books");
        const title = $(this).data("title");

        // Populate the edit form with the existing book details
        $("#edit-book-id").val(bookId);
        $("#edit-author").val(author);
        $("#edit-nb_of_books").val(nb_of_books);
        $("#edit-title").val(title);

        // Show the edit form
        $("#edit-book-form").show();
        $("#add-book-form").hide();
    });

    // Handle form submission for updating a book
    $("#update-book-form").submit(function(event) {
        event.preventDefault();

        const updatedBook = {
            id: $("#edit-book-id").val(),
            author: $("#edit-author").val(),
            nb_of_books: $("#edit-nb_of_books").val(),
            title: $("#edit-title").val()
        };

        $.ajax({
            type: "POST",
            url: backendUrl1+"/UpdateBook",
            contentType: "application/json",
            data: JSON.stringify(updatedBook),
            success: function(response) {
                alert(response); // Show success message

                // Update the book details in the table
                const row = $(`button[data-id='${updatedBook.id}']`).closest("tr");
                row.find("td:eq(0)").text(updatedBook.author);
                row.find("td:eq(1)").text(updatedBook.nb_of_books);
                row.find("td:eq(2)").text(updatedBook.title);

                // Hide the edit form and reset it
                $("#edit-book-form").hide();
                $("#update-book-form")[0].reset();
            },
            error: function(error) {
                alert("Error updating book: " + error.responseText);
            }
        });
    });




    // Handle delete book button click
    $(document).on("click", ".delete-book-btn", function() {
        const bookId = $(this).data("id");
        const row = $(this).closest("tr");

        const bookAuthor = row.find(".book-author").text();
        const bookNbOfBooks = row.find(".book-nb_of_books").text();
        const bookTitle = row.find(".book-title").text();

        if (confirm("Are you sure you want to delete this book?")) {
            const bookToDelete = {
                id: bookId,
                author: bookAuthor,
                nb_of_books: parseInt(bookNbOfBooks, 10),
                title: bookTitle
            };
            $.ajax({
                type: "POST",
                url: backendUrl1+"/DeleteBook",
                contentType: "application/json",
                data: JSON.stringify(bookToDelete),
                success: function(response) {
                    alert(response); // Show success message
                    // Remove the deleted book row from the table
                    row.remove();
                },
                error: function(xhr) {
                    // Log the full error for debugging
                    console.error("Error deleting book:", xhr.responseText);
                    alert("Error deleting book: " + xhr.responseText);
                }
            });
        }
    });

});