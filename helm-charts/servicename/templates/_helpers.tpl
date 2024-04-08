{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "api.name" -}}
{{- if .Values.service.version -}}
	{{- $name := default .Chart.Name .Values.nameOverride | trunc 59 | trimSuffix "-" -}}
	{{printf "%s-%s" $name .Values.service.version -}}
{{- else -}}
	{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "api.fullname" -}}
	{{- if .Values.fullnameOverride -}}
			{{- if .Values.deployment.version -}}
				{{- .Values.fullnameOverride | trunc 59 | trimSuffix "-" -}}-{{- .Values.deployment.version -}}
			{{- else -}}
				{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
			{{- end -}}
	{{- else -}}
		{{- $name := default .Chart.Name .Values.nameOverride -}}
			{{- if contains $name .Release.Name -}}
				{{- if .Values.deployment.version -}}
					{{- $name := printf "%s-%s" .Release.Name | trunc 59 | trimSuffix "-" .Values.deployment.version -}}
				{{- else -}}
					{{- $name := .Release.Name | trunc 63 | trimSuffix "-" -}}
				{{- end -}}
			{{- else -}}
				{{- if .Values.deployment.version -}}
					{{- $name := printf "%s-%s" .Release.Name $name | trunc 59 | trimSuffix "-" -}}
					{{- $name := printf "%s-%s" $name .Values.deployment.version -}}
				{{- else -}}
					{{- $name := printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
				{{- end -}}
			{{- end -}}
	{{- end -}}
{{- end -}}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "api.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Common labels
*/}}
{{- define "api.labels" -}}
cmdb_appid: {{ .Values.cmdb_appid }}
app.kubernetes.io/name: {{ include "api.name" . }}
helm.sh/chart: {{ include "api.chart" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- if .Values.deployment.version }}
version: {{ .Values.deployment.version }}
{{- end }}
{{- end -}}

{{/*
{{- define "api.matchlabels" -}}
{{- if .Values.service.version -}}
app.kubernetes.io/name: {{- .Values.fullnameOverride | trunc 59 | trimSuffix "-" -}}-{{- .Values.service.version -}}
{{- else -}}
{{- if .Values.deployment.version -}}
app.kubernetes.io/name: {{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
version: {{- .Values.deployment.version -}}
{{- else -}}
app.kubernetes.io/name: {{ include "api.name" . }}
app.kubernetes.io/instance: {{ .Release.name }}
{{- end -}}
{{- end -}}
{{- end -}}
*/}}

{{- define "api.kname" -}}
{{- if .Values.service.version -}}
	{{- .Values.fullnameOverride | trunc 59 | trimSuffix "-" -}}-{{- .Values.service.version -}}
{{- else -}}
	{{- if .Values.deployment.version -}}
		{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
	{{- else -}}
		{{ include "api.name" . }}
	{{- end -}}
{{- end -}}
{{- end -}}

{{- define "api.matchlabels" -}}
app.kubernetes.io/name: {{ include "api.kname" . }}
{{- if .Values.deployment.version }}
version: {{ .Values.deployment.version }}
{{- else }}
app.kubernetes.io/instance: {{ .Values.release.name }}
{{- end -}}
{{- end -}}

{{/* working
{{- define "api.matchlabels" -}}
app.kubernetes.io/name: {{ include "api.name" . }}
{{- if .Values.service.version }}
version: {{ .Values.service.version }}
{{- end -}}
{{- if .Values.deployment.version }}
app.kubernetes.io/instance: {{ include "api.name" . }}
{{- else }}
app.kubernetes.io/instance: {{ .Release.name }}
{{- end -}}
{{- end -}}
*/}}


{{- define "api.metalabels" -}}
app.kubernetes.io/name: {{ include "api.name" . }}
app.kubernetes.io/instance: {{ include "api.name" . }}
{{- if .Values.deployment.version -}}
version: {{ .Values.deployment.version }}
{{- end }}
{{- end -}}

{{- define "serviceaccount.name" -}}
{{- if .Values.config.version -}}
{{ .Values.serviceAccount.name }}-{{ .Values.config.version }}
{{- else -}}
{{ .Values.serviceAccount.name }}
{{- end -}}
{{- end -}}

{{- define "hpa.name" -}}
{{- if .Values.deployment.version -}}
{{ .Values.fullnameOverride }}-hpa-{{ .Values.deployment.version }}
{{- else -}}
{{ .Values.fullnameOverride }}-hpa
{{- end -}}
{{- end -}}
