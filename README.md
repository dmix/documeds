# [documeds](https://github.com/dmix/documeds)

An application to assist people in finding the ideal medication stack for their health condition.

### Overview

People often take multiple medications over the course of their illness. Various factors determine whether a drug is best for a person. Such as how well the drug works, medication prices, and amount of side-effects caused. As a result almost all treatments invole experimenting with different medications until a combination is found that works best. 

Documeds was created to make this process easier, to help narrow down what might work for you, and provide a secondary data-source - in addition to your doctor - regarding what your options are.

### How it works

The idea for this program is to capture data on a) mediciations and b) health conditions, then correlate which mediations are used to treat which condition. From there we can data-mine each drug for:

- average price per day
- # of prescriptions made by doctors each year nationwide
- efficay rates from published studies
- user-generated reviews
- scoping all data to age/gender/seriousness of illness/etc

Then users will be presented with a UI to search medications, filter by their illness name/age/gender/seriouness, and see which drugs (or combinations of drugs) might be worth checking out. Users can then track their experiences they've had with each drug, publish it to the site (anonymously) and share it with other people with their conditition.. 

### Current status:

Currently focused on two things:

1) Scraping medication data from a variety of sources around the internet, data mining to build structure and  relationships between the data and publishing the content with JSON APIs. 

2) A backbone.js client for keeping track of the medication and supplements you are taking

Built with:

- Clojure
- Noir
- Redis
- Aleph
- Enlive
- Backbone.js

## Usage

```bash
lein deps
lein run -m documeds.server :prod
```

Also run Redis on localhost, default port with no password.

## License

Code: GPLv2

https://documeds.com - UI/written content Copyright (C) 2015 Dan McGrady https://dmix.ca
