# FetchExercise App 
Fetch Exercise

### Description 
This is a native Android app in Kotlin or Java that retrieves the data from https://fetch-hiring.s3.amazonaws.com/hiring.json.<br>
It uses key points that I have learn during my career as an Android Software Enginee: 
- **Clean Architecture principles**
- **best practices** 
- **personal implementations**

### Preliminaries

As stated in the description the application is based on Clean Architecture patern which implements SOLID principles. The following diagram is the typical one that was has been used as a general practice for building software projects.

  <img width="306" alt="image" src="https://github.com/javarmgar/FetchExercise/assets/21993768/b175bb3e-707b-4ed9-98ef-7e2ecbf5418f">


### Fetch Exercise App Architecture

As we are working in an Android project the Clean Architecture has been applied to the android App and some changes and adjusments were done.

Here's a diagram of the Fetch Exercise App Architecture.<br>
  <img width="306" alt="image" src="https://github.com/javarmgar/FetchExercise/assets/21993768/b175bb3e-707b-4ed9-98ef-7e2ecbf5418f">

#### Android Components
Fetch Exercise App is composed by the following android components.
- **core component**:
  - It holds only abstract entities i.e. interfaces and abstract classes, by decoupling abstract entities from concrete implementations we can make a reusable project. This component can be later reusable from other components namely Android TV apps, automobile apps and so on.
  - Additionally this component DO NOT have access to Android SDK, forcing developers to correctly implement **SOLID** principles
- **app component**: This is the actual app, it has access to android SDK and libraries. Core component is imported as a local dependency, then concrete classes implement core interfaces and abstract classes.


#### Layers
Fetch Exercise App Architecture components are composed themselves by the following layers.
- **core component**:
  - **data**: Data abstractions for: models, datasources, repositories, mappers <br>
      - (e.g. **_HiringRepository, HiringRemoteDataSource, HiringResponseModel_**)
  - **domain**: Abstractions for Use Cases (also known as interactors):<br>
      - (e.g. **GetHiringUseCase**)
- **app component**:
  - **framework**: It contains concrete implementations of the abstractions declared in core component. It comprises 3 packages:
    - **dataimpl**: Concrete implementations for: models, datasources, repositories, mappers <br>
      - (e.g. **_HiringRepositoryImpl, HiringRemoteDataSourceImpl, HiringResponseModelImpl_**)
    - **domainimpl**: Concrete implementations for: Use cases <br>
      - (e.g. **_GetHiringUseCaseImpl_**)
    - **library**: It holds several classes: keys, managers, utils...
  - **presentation**: This layers hold classes that are involved in the UI creation process: Activities, Views and ViewModels.
    - (e.g. **_MainActivity, HiringViewHolder, MainViewModel_**)
  - modules: This is not accurately a layer but a package. It is responsible for providing instances to the Dagger-Hilt application Graph via abstract definitions alongside Hilt and Dagger annotations.
    - (e.g. **_NetworkModule, RemoteDataSourceModule, RepositoryModule..._**)

  
### Fetch Exercise App Architecture Description
The previous layer description section enlisted and briefly details each layer purpose. However internally each layer at the same time compounds several packages (e.g **base** package which is used to holds base/parent classes). The complete directory structure of the project is being displayed here [File Directory Structure](https://github.com/javarmgar/FetchExercise/blob/main/FileDirectoryStructure.md)

### Screenshots and videos 
