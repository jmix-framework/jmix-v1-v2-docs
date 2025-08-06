# Contributing to Jmix Guides

Thank you for your interest in contributing to the Jmix documentation! This guide will walk you through the process of creating and integrating a new example project for Jmix guides. We’ll use the jmix-petclinic-2 repository as the base example to help you set up, adapt, and publish a new project within the Jmix framework.

## Guide Contribution

To create a new Jmix guide, follow these steps:

### How to Create an Example for a New Jmix Guide

In order to write a guide based on the Jmix petclinic the following steps should be taken:

#### Step 0: Setup a temporary environment variable

Set up an environment variable to define the core name of your guide. This will help keep naming consistent across commands:

```bash
export GUIDE_NAME=business-logic
```

#### Step 1: Clone the Base Repository

Clone the base jmix-petclinic-2 repository to your local machine:

```bash
git clone git@github.com:jmix-framework/jmix-petclinic-2.git
```

This repository provides a template with existing configurations that simplify the setup for new examples.

#### Step 2: Set Up a New Repository for Your Guide

Make a copy of the cloned repository, renaming it according to the topic you’re covering:

```bash
cp -R jmix-petclinic-2 jmix-$GUIDE_NAME-sample
```

Follow the jmix-topic-name-sample naming pattern to keep repository names consistent.

Then create a new repository in the Jmix GitHub organization:

```bash
gh repo create jmix-framework/jmix-$GUIDE_NAME-sample --public
```

#### Step 3: Configure Git Remotes

Point your local repository to the new GitHub repository:

```bash
git remote set-url origin git@github.com:jmix-framework/jmix-$GUIDE_NAME-sample.git
```

Then add the original jmix-petclinic-2 repository as the upstream remote, so you can easily pull updates from it:

```bash
git remote add upstream git@github.com:jmix-framework/jmix-petclinic-2.git
```

You can confirm your remote configuration via:

```bash
git remote -v
```

#### Step 4: Adjust Project Settings

In settings.gradle, change the rootProject.name from jmix-petclinic to match your new project name:

```bash
echo "rootProject.name = 'jmix-$GUIDE_NAME-sample'" > settings.gradle
```

#### Step 5: Implement Your Example

Now you’re ready to develop your example! Add the custom business logic, configurations, or other adjustments specific to your guide’s topic.

#### Step 6: Keep Up with Upstream Changes

Occasionally, update your example with any new changes from the jmix-petclinic-2 upstream repository to stay consistent with base improvements:

```bash
git fetch upstream
git checkout main
git merge upstream/main
```

### Integrating Your Guide with the Jmix Docs

Once your example is ready, it’s time to integrate it with the main Jmix documentation repository.

#### Step 1: Rename the Docs Directory

Change the petclinic-guide directory to match the new guide name:

```bash
mv doc/modules/petclinic-guide doc/modules/$GUIDE_NAME-guide
```

#### Step 2: Integrate with the Main Docs Repository

In the main Jmix Docs repository, update the settings.gradle to clone your example locally. For example:

```groovy
cloneOrPull('https://github.com/jmix-framework/jmix-$GUIDE_NAME-sample', 'external/jmix-$GUIDE_NAME-sample')
includeBuild 'external/jmix-$GUIDE_NAME-sample'
```

Additionally, you have to add the source root to the Antora playbook `antora-playbook.yml`:

```yaml
content:
  sources: 
  - url: ./external/jmix-$GUIDE_NAME-sample
    branches: HEAD
    start_path: doc
```

On the `antora-playbook.ci.yaml` the definition also needs to be there, but is slightly different:

```yaml
content:
  sources:
    - url: https://github.com/jmix-framework/jmix-$GUIDE_NAME-sample.git
      branches:
        - main
      start_path: doc
```

Finally, you have to add the guide to the main navigation Asciidoc file `nav.adoc`:

```asciidoc
* xref:ROOT:guides.adoc[]
** xref:$GUIDE_NAME-guide:index.adoc[]
```

From now on you are able to write the docs directly in the jmix-docs IntelliJ IDEA project, which allows the Asciidoc plugin to recognise xref references correctly.

Once your Jmix guide is complete, open a Pull Request in the Jmix Docs repository. The Jmix team will review and collaborate with you on any necessary adjustments.
