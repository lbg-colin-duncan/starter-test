@Regression
Feature: OPS endpoints are accessible

 Scenario Outline: Endpoint should be available
  Given the '<scenario>' needs to be validated
  When called '<endpoint>' endpoint
  Then should return '<statusCode>'

  Examples:
  | scenario           | endpoint   | statusCode |
  | HealthCheck        | /health    | 200        |
  | GetCustomerDetails | /customers | 200        |
