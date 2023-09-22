resource "google_secret_manager_secret_iam_binding" "secret_binding_toastmaster_email_from" {
  secret_id = "projects/908519591128/secrets/toastmaster-email-from"
  role      = "roles/secretmanager.secretAccessor"
  members   = ["serviceAccount:908519591128-compute@developer.gserviceaccount.com"]
}

resource "google_secret_manager_secret_iam_binding" "secret_binding_toastmaster_email_to" {
  secret_id = "projects/908519591128/secrets/toastmaster-email-to"
  role      = "roles/secretmanager.secretAccessor"
  members   = ["serviceAccount:908519591128-compute@developer.gserviceaccount.com"]
}

resource "google_secret_manager_secret_iam_binding" "secret_binding_toastmaster_spring-mail-password" {
  secret_id = "projects/908519591128/secrets/toastmaster-spring-mail-password"
  role      = "roles/secretmanager.secretAccessor"
  members   = ["serviceAccount:908519591128-compute@developer.gserviceaccount.com"]
}

resource "google_secret_manager_secret_iam_binding" "secret_binding_toastmaster_spring-mail-username" {
  secret_id = "projects/908519591128/secrets/toastmaster-spring-mail-username"
  role      = "roles/secretmanager.secretAccessor"
  members   = ["serviceAccount:908519591128-compute@developer.gserviceaccount.com"]
}