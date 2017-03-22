Feature: Adding new topics to the topic tree.

  Scenario: Adding a new topic.
    * Given a maintainer M.
    * When adding topic A as maintainer M.
    * Then there should be a topic A maintained by M.

  Scenario: Adding a topic a second time as same maintainer.
    * Given a maintainer M.
    * Given a topic A maintained by M.
    * When adding topic A as maintainer M.
    * Then there should be a topic A maintained by M.

  Scenario: Trying to add a topic already maintained by another maintainer.
    * Given a maintainer M.
    * Given a maintainer N.
    * Given a topic A maintained by N.
    * When adding topic A as maintainer M.
    * Then there should be a failure message.
    * Then there should be no topic A maintained by M.

  Scenario: Trying to add a topic as subtopic to another topic.
    * Given a maintainer M.
    * Given a topic C maintained by M.
    * When adding topic A as subtopic of C as maintainer M.
    * Then there should be a topic A maintained by M.

  Scenario: Trying to add a topic as subtopic to another topic not maintained by myself.
    * Given a maintainer M.
    * Given a maintainer N.
    * Given a topic C maintained by N.
    * When adding topic A as subtopic of C as maintainer M.
    * Then there should be topic A maintained by M.