# Philipp Lackner's News App tutorial (with my features)

### Technologies stack

- Navigation component
- ViewBinding
- Retrofit2
- OkHttp3
- Coroutines
- Room
- LiveData

### Decomposition of process
Made decomposition of app development process and write a table to track my time to realization and summarize task completion

| Task                                 | Description                                                                                                                             | Duration   |
|--------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|------------|
| Layouts                              | `BottomNavigationView`<br/>`News feed`<br/>`Saved news`<br/>`Search`<br/>`Arictle page`<br/>`Article preview` (item for `RecyclerView`) | 2 hours    |
| Navigation (`ui` repo)               | Navigation component (fragments files was implemented on the previous step). Set up (and read about) animations                         | 30 minutes |
| Retrofit setup (`api` repo)          | API interface, response classes, dependencies and internet permission                                                                   | 1 hour     |
| Room setup (`models` and `db` repos) | Entity, DAO, Database and type converters                                                                                               | 40 minutes |
| RecyclerView setup                   |                                                                                                                                         |            |
| Architecture                         |                                                                                                                                         |            |
| Handling response                    |                                                                                                                                         |            |
| Search setup                         |                                                                                                                                         |            |
| WebView setup                        |                                                                                                                                         |            |
| Writing to DB                        |                                                                                                                                         |            |
| Pagination                           |                                                                                                                                         |            |
| Internet connection checking         |                                                                                                                                         |            |

### My features

- [x] ViewBinding usage 
- [x] Converting `id` of `Source` too (not replacing it with duplicated `name`) 
