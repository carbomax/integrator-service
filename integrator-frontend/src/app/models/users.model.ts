export interface User {
  id:string,
  role:string,
  enabled:boolean
  name?:string,
  email?:string,
  password?: string,
  image?:string,
}
