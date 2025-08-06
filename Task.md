# Bookstore CRUD API Checklist

- [x] **POST /admin/register** - admin registration
- [x] **POST /appuser/register** - appuser registration 
- [x] **POST /bookstore/create-book** - Create a new book
- [x] **GET /bookstore/books** - Get all books
- [x] **GET /bookstore/books/{id}** - Get book by ID
- [ ] **PUT /bookstore/books/{id}** - Update book
- [ ] **DELETE /bookstore/books/{id}** - Delete book
- [x] **POST /bookstore/create-category** - create a new category
- [x] **GET /bookstore/categories** Get all category
- [x] **GET /bookstore/categories/{id}** Get category by ID
- [ ] **PUT /bookstore/categories/{id}** - Update category
- [ ] **DELETE /bookstore/categories/{id}** - Delete category
- [x] **POST /bookstore/create-author** - create a new author
- [x] **GET /bookstore/authors** Get all author
- [x] **GET /bookstore/authors/{id}** Get author by ID
- [ ] **PUT /bookstore/authors/{id}** - Update author
- [ ] **DELETE /bookstore/authors/{id}** - Delete author
- [x] test the relationship between author category book.
- [ ] create home page
- [x] **GET /bookstore/appuser-books** - list all appuser's books. create shelf model, user many to many book
- [x] **POST /bookstore/appuser-addbook** - appuser buy book
- [x] **GET /admin/approve/{appuser_id}** - appove appuser account

# Debug
login in home 
    -> /currentuser (check if username is not anonymousUser)
    -> /debug/auth (show Authentication object details)

logout clear session and clean SecurityContextHolder