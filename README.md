# Getting Started


### Technical challenge (for developers)

- Use any architecture you are comfortable with
- Use modern Java (17+)
- Use Spring boot
  - Feel free to add any additional libraries
- Use any database NoSQL is preferred (please use embedded)
- The code must be tested
- The service should be easy to run (e.q. docker-compose)
- The service should expose a REST API that can be interacted with swagger-ui & be functional.
- When the service starts it should have a bunch of fake data to play with.

### Business Case:
#### Creative Management

| Entity   |                                                                    Description                                                                     |
|----------|:--------------------------------------------------------------------------------------------------------------------------------------------------:|
| Brand    |                                                            Is a retailer of product(s)                                                             |
| Creative | Are digital marketing files for advertisements, published to a brand campaign combination. A single brand can have tens of thousands of creatives. |
| Campaign |     Is a collection of creatives to achieve some advertising goal, a Brand runs many campaigns, campaigns can contain thousands of creatives.      |

- Example data models for each entity have been created for you in the `com.smartassets.challenge.model` package, 
it's not necessary to add additional attributes to the models (if you want to feel free to add additional fields that you think may be applicable to the domain) to keep things simple the 'creativeUrl' in a creative
is a string, we are pretending it represents a URL to a file stored in a CDN that was magically created for you.

- Create a simple web application for managing creatives.
- An end user should be able to:
  - Create a brand
  - Create a campaign for a specific brand
  - Upload a creative for a specific brand campaign combination.

- In this example, Authentication is not required.

# Example resources

#### Brand:
  - Create a brand
  - List all brands

#### Campaign:
  - Create a campaign for a specific brand
  - List all campaigns for a specific brand

#### Creative:
  - Upload a creative for a specific brand campaign combination
  - List creatives for a specific brand campaign combination

Feel free to create any additional resources you think would be necessary to make it simple for a UI to consume if
there is a large number of each entity type.

#### Steps to proceed:
 - Please create a private Github repository with your solution, and share it with us.
   - Eric Walzthony <eric.walzthony@smartassets.ai>
   - Andrey Nunez <andrey.nunez@smartassets.ai>
   - Nathan Hotchkin <nathan.hotchkin@smartassets.ai>