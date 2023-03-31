# Philipp Lackner's News App tutorial (with my changes)

### Technologies stack

- Navigation component
- ViewBinding
- Retrofit2
- OkHttp3
- Coroutines
- Room
- LiveData
- MVVM
- Glide
- Material Design

### Decomposition of process
Made decomposition of app development process and write a table to track my time to realization and summarize task completion

| Task                                 | Description                                                                                                                             | Duration          |
|--------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| Layouts                              | `BottomNavigationView`<br/>`News feed`<br/>`Saved news`<br/>`Search`<br/>`Arictle page`<br/>`Article preview` (item for `RecyclerView`) | 2 hours           |
| Navigation (`ui` repo)               | Navigation component (fragments files was implemented on the previous step). Set up (and read about) animations                         | 30 minutes        |
| Retrofit setup (`api` repo)          | API interface, response classes, dependencies and internet permission                                                                   | 1 hour            |
| Room setup (`models` and `db` repos) | Entity, DAO, Database and type converters                                                                                               | 40 minutes        |
| RecyclerView setup (`adapters` repo) | Adapter and ViewHolder with DiffUtil                                                                                                    | 40 minutes        |
| Architecture                         | ViewModel, ViewModel factory, wrapper class for Retrofit responses                                                                      | 1 hour            |
| Handling response                    | Set up streams between `NewsRepository` and `NewsViewModel`, `NewsViewModel` and `NewsFeedFragment`                                     | 1 hour            |
| Search setup                         | Set up searching function and streams for searching                                                                                     | 45 minutes        |
| WebView setup                        | Set up argument for `ArticlePageFragment`, passing data through nav component and debug `java.lang.NullPointerException`                | 1 hour 30 minutes |
| Writing to DB                        |                                                                                                                                         |                   |
| Pagination                           |                                                                                                                                         |                   |
| Internet connection checking         |                                                                                                                                         |                   |

### Changes/features 

- [x] Material Design style
- [x] ViewBinding usage 
- [x] Converting `id` of `Source` too (not replacing it with duplicated `name`) 
- [x] Define ViewModel factory in its file, not in separate class
- [x] Define wrapper responses class with sealed interface, not sealed class
- [x] Using `Safe Args` generated class for directions
- [x] Using `Parcelable`

### My questions
1. Is it okay to use one ViewModel across multiple Fragments?
2. Is it okay to get access Activity variable from Fragments (like `viewModel = (activity as MainActivity).viewModel`)?
3. How to check API responses with codes?

### To-do
- [ ] redesign app
- [ ] refactor properties
- [ ] refactor methods

