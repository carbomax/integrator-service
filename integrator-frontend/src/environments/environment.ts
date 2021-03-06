// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  uri_backend: 'http://localhost:9999/',
  redirect_uri: `https://auth.mercadolibre.com.uy/authorization?response_type=code&client_id=663620176789077&state=${Math.floor(Math.random() * 100)}&redirect_uri=http://localhost:4200/meli_accounts`
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
