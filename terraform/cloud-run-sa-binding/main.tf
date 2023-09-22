resource "google_cloud_run_service_iam_binding" "service_binding_toastmaster" {
  location = "us-central1"
  service  = "toastmaster"
  role     = "roles/run.invoker"
  members  = ["serviceAccount:${google_service_account.sa_cloud_run.email}"]
}