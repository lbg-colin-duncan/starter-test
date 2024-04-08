{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "istio-routes.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "istio-routes.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- if contains $name .Release.Name -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
{{- end -}}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "istio-routes.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Common labels
*/}}
{{- define "istio-routes.labels" -}}
app.kubernetes.io/name: {{ include "istio-routes.name" . }}
helm.sh/chart: {{ include "istio-routes.chart" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end -}}

{{- define "virtualservice.name" -}}
{{- if .Values.service.version -}}
{{ .Values.virtualservice.name | trunc 59 | trimSuffix "-" }}-{{ .Values.service.version }}
{{- else -}}
{{ .Values.virtualservice.name | trunc 59 | trimSuffix "-" }}
{{- end -}}
{{- end -}}

{{- define "destinationrule.name" -}}
{{- if .Values.service.version -}}
{{ .Values.destinationrule.name | trunc 59 | trimSuffix "-" }}-{{ .Values.service.version }}
{{- else -}}
{{ .Values.destinationrule.name | trunc 59 | trimSuffix "-" }}
{{- end -}}
{{- end -}}

{{- define "requestauth.name" -}}
{{- if .Values.service.version -}}
{{ .Values.resourceBaseName }}-{{ .Values.service.version }}-ra
{{- else -}}
{{ .Values.resourceBaseName }}-ra
{{- end -}}
{{- end -}}

{{- define "authpolicy.name" -}}
{{- if .Values.service.version -}}
{{ .Values.resourceBaseName }}-{{ .Values.service.version }}-ap-deny
{{- else -}}
{{ .Values.resourceBaseName }}-ap-deny
{{- end -}}
{{- end -}}