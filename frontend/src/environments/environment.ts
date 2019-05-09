// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  name: 'dev',
  pwaActive: false,
  firebaseConfig : {
    apiKey: "AIzaSyCmuZvoeHr-WIcpIaqj7Vh5keDW6zzKWc8",
/*    authDomain: "YOUR_AUTH_DOMAIN",
    databaseURL: "YOUR_DATABASE_URL",*/
    projectId: "werewolf-5cc36",
    storageBucket: "gs://werewolf-5cc36.appspot.com/profilepictures",
/*    messagingSenderId: "YOUR_MESSAGING_SENDER_ID"*/
  }
};


/*
 * In development mode, to ignore zone related error stack frames such as
 * `zone.run`, `zoneDelegate.invokeTask` for easier debugging, you can
 * import the following file, but please comment it out in production mode
 * because it will have performance impact when throw error
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
